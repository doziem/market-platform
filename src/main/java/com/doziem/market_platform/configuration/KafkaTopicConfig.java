package com.doziem.market_platform.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.product-notification:product-notification}")
    private String productTopic;

    @Value("${kafka.topic.verification-email:verification-email-topic}")
    private String verificationEmailTopic;

    @Value("${kafka.topic.product-alerts:product-alerts-topic}")
    private String productAlertsTopic;

    @Bean
    public NewTopic productAlertsTopic() {
        return TopicBuilder.name(productAlertsTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic productTopic() {
        return TopicBuilder.name(productTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic verificationEmailTopic() {
        return TopicBuilder.name(verificationEmailTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
