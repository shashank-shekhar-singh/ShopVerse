package com.shopverse.userservice.messaging.events;

import com.shopverse.userservice.enums.EventType;
import org.slf4j.MDC;

import java.util.UUID;

import static com.shopverse.common.config.CorrelationIdFilter.CORRELATION_ID_LOG_KEY;

public class UserCreatedEvent extends BaseEvent {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    public UserCreatedEvent(UUID userId, String email, String firstName, String lastName, String phone) {
        super(userId, EventType.USER_CREATED, MDC.get(CORRELATION_ID_LOG_KEY));
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
}
