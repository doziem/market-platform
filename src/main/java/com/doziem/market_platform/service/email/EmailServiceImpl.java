package com.doziem.market_platform.service.email;

import com.doziem.market_platform.payload.dto.ProductNotification;
import com.doziem.market_platform.payload.dto.VerificationEmailMessage;
import com.doziem.market_platform.model.User;
import com.doziem.market_platform.service.DepartmentAlertRecipientService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final DepartmentAlertRecipientService recipientService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${alert.email.from}")
    private String fromEmail;

    @Value("${kafka.topic.verification-email:verification-email-topic}")
    private String verificationEmailTopic;

    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 3000)
    )
    @Override
    public void sendUrgentAlert(ProductNotification notification) {
        List<String> recipients = recipientService.resolveRecipients(notification);

        Context context = new Context();
        context.setVariable("notification", notification);

        String html = templateEngine.process("stock-alert.html", context);

        for (String to : recipients) {
            sendHtmlEmail(to, html, notification.getSeverity());
        }
    }

    @Override
    public void sendVerificationEmail(User user, String verificationLink) {
        VerificationEmailMessage message = VerificationEmailMessage.builder()
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .verificationLink(verificationLink)
                .build();
        kafkaTemplate.send(verificationEmailTopic, user.getEmail(), message);
    }

    @KafkaListener(
            topics = "${kafka.topic.verification-email:verification-email-topic}",
            groupId = "${spring.kafka.consumer.group-id:market-platform-group}",
            concurrency = "1"
    )
    public void consumeVerificationEmail(VerificationEmailMessage message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(message.getEmail());
            helper.setSubject("Verify your email address");

            Context context = new Context();
            context.setVariable("displayName", message.getDisplayName());
            context.setVariable("verificationLink", message.getVerificationLink());

            String html = templateEngine.process("email-verification.html", context);
            helper.setText(html, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send verification email to {}: {}", message.getEmail(), e.getMessage(), e);
            throw new RuntimeException("Verification email send failed", e);
        }
    }

    private void sendHtmlEmail(String to, String html, String severity) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("[" + severity + "] Stock Alert");
            helper.setText(html, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Email send failed", e);
        }
    }

    @Recover
    public void recover(Exception ex, ProductNotification notification) {
        log.error("Email failed after retries. Sending to DLQ", ex);
        kafkaTemplate.send("product-alerts-email-dlq", notification.getProductId(), notification);
    }
}
