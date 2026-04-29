package com.shopverse.userservice.messaging.publisher;

import com.shopverse.userservice.messaging.events.BaseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(UserEventPublisher.class);

    public UserEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(BaseEvent event) {
        kafkaTemplate.send("user-events", String.valueOf(event.getUserId()), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        logger.error("Failed to send event for userId={}", event.getUserId(), ex);
                        // handle failure
                    } else {
                        logger.info("Event sent successfully. Offset={}",
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
