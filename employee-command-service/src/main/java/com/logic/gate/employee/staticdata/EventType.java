package com.logic.gate.employee.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventType {
    CREATE_EVENT("Create Event"),
    UPDATE_EVENT("Update Event");

    private final String eventType;

    public String getEventType() {
        return eventType;
    }
}
