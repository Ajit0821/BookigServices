package com.booking.service.enums;

public enum BookingStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    private final String status;

    // Constructor to set the status
    BookingStatus(String status) {
        this.status = status;
    }

    // Getter method to retrieve the status
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
