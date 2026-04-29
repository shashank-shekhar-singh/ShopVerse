package com.shopverse.userservice.messaging.events;

import com.shopverse.userservice.enums.AccountStatus;
import com.shopverse.userservice.enums.EventType;
import org.slf4j.MDC;

import java.util.UUID;

import static com.shopverse.userservice.config.CorrelationIdFilter.CORRELATION_ID_LOG_KEY;

public class UserUpdatedEvent extends BaseEvent {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private AccountStatus status;

    public UserUpdatedEvent(UUID userId, String email, String firstName, String lastName, String phone, AccountStatus status) {
        super(userId, EventType.USER_UPDATED, MDC.get(CORRELATION_ID_LOG_KEY));
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.status = status;
    }
}
