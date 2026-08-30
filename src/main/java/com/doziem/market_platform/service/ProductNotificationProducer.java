package com.doziem.market_platform.service;

import com.doziem.market_platform.payload.dto.ProductNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProductNotificationProducer {

    private static final Logger log = LoggerFactory.getLogger(ProductNotificationProducer.class);

    private final KafkaTemplate<String, ProductNotification> kafkaTemplate;

    @Value("${kafka.topic.product-alerts:product-alerts-topic}")
    private String productAlertsTopic;

    public ProductNotificationProducer(KafkaTemplate<String, ProductNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendStockAlert(ProductNotification notification) {
        try {
            String key = "product-" + notification.getProductId();

            CompletableFuture<SendResult<String, ProductNotification>> future =
                    kafkaTemplate.send(productAlertsTopic, key, notification);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Product alert sent successfully: Topic={}, Partition={}, Offset={}, ProductId={}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset(),
                            notification.getProductId());
                } else {
                    log.error("Failed to send product alert for ProductId={}: {}",
                            notification.getProductId(), ex.getMessage());
                }
            });
        } catch (Exception e) {
            log.error("Error sending product notification to Kafka: {}", e.getMessage(), e);
        }
    }
}
