package com.example.webservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
public enum DeliveryStatus {
    PAID("PAID"),
    PENDING("PENDING"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED");

    private final String statusName;

    DeliveryStatus(String statusName) {
        this.statusName = statusName;
    }

    @JsonValue
    public String getStatusName() {
        return statusName;
    }

    @JsonCreator
    public static DeliveryStatus fromStatusName(String statusName) {
        for (DeliveryStatus status : DeliveryStatus.values()) {
            if (status.getStatusName().equalsIgnoreCase(statusName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status name: " + statusName);
    }
}
