package com.shopverse.userservice.messaging.events;

import com.shopverse.userservice.enums.EventType;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public abstract class BaseEvent {
    private UUID userId;
    private String eventId;
    private EventType eventType;
    private String correlationId;
    private Instant occurredAt;
    private int version;

    protected BaseEvent(UUID userId, EventType eventType, String correlationId) {
        this.userId = userId;
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.occurredAt = Instant.now();
        this.correlationId = correlationId;
    }
}
