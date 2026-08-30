package com.doziem.market_platform.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "productTopic", groupId = "market-platform-group")
    public void consumeProductCreation(String message){

    }
}
