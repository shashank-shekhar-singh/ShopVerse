package com.shopverse.userservice.messaging.events;

import com.shopverse.userservice.enums.EventType;
import org.slf4j.MDC;

import java.util.UUID;

import static com.shopverse.userservice.config.CorrelationIdFilter.CORRELATION_ID_LOG_KEY;

public class UserDeletedEvent extends BaseEvent {

    private String email;

    public UserDeletedEvent(UUID userId, String email) {
        super(userId, EventType.USER_DELETED, MDC.get(CORRELATION_ID_LOG_KEY));
        this.email = email;
    }
}
