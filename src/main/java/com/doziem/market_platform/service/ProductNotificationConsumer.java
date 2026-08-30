package com.doziem.market_platform.service;

import com.doziem.market_platform.payload.dto.ProductNotification;
import com.doziem.market_platform.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductNotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProductNotificationConsumer.class);

    private final EmailService emailService;


    @KafkaListener(
            topics = "${kafka.topic.product-alerts:product-alerts-topic}",
            groupId = "${spring.kafka.consumer.group-id:market-platform-group}",
            concurrency = "3"
    )
    public void consumeStockAlert(ProductNotification notification) {
        log.info("Received stock alert: {}", notification);

        try {
            switch (notification.getSeverity()) {
                case "CRITICAL" -> handleCriticalAlert(notification);
                case "HIGH" -> handleHighPriorityAlert(notification);
                case "MEDIUM" -> handleMediumPriorityAlert(notification);
                default -> handleLowPriorityAlert(notification);
            }

            processNotificationByType(notification);

        } catch (Exception e) {
            log.error("Error processing stock notification: {}", e.getMessage(), e);
        }
    }

        private void handleCriticalAlert(ProductNotification notification) {
            log.error("CRITICAL ALERT: {}", notification.getMessage());
            emailService.sendUrgentAlert(notification);
            // emailService.sendUrgentAlert(notification);
            // smsService.sendAlert(notification);

            // Could also trigger auto-reorder if implemented
            // purchaseOrderService.createAutoReorderRequest(notification.getProductId());
        }

        private void handleHighPriorityAlert(ProductNotification notification) {
            log.warn("HIGH PRIORITY ALERT: {}", notification.getMessage());
            // Send email notification
            // emailService.sendHighPriorityAlert(notification);
        }

        private void handleMediumPriorityAlert(ProductNotification notification) {
            log.warn("MEDIUM PRIORITY ALERT: {}", notification.getMessage());
            // Add to admin dashboard
            // dashboardService.addWarning(notification);
        }

        private void handleLowPriorityAlert(ProductNotification notification) {
            log.info("LOW PRIORITY ALERT: {}", notification.getMessage());
            // Log to system or add to daily report
        }

        private void processNotificationByType(ProductNotification notification) {
            switch (notification.getType()) {
                case STOCK_DEPLETED ->
                        log.warn("Stock depleted for product: {}", notification.getProductName());
                case INSUFFICIENT_STOCK ->
                        log.warn("Insufficient stock for product: {}", notification.getProductName());
                case NULL_STOCK ->
                        log.error("Null stock detected for product: {}", notification.getProductName());
                case LOW_STOCK_WARNING ->
                        log.info("Low stock warning for product: {}", notification.getProductName());
            }

        }
}
