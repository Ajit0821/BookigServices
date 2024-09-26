package com.booking.service.dto;

import com.booking.service.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsDto {
    private Long bookingId;
    private PassengerDetails passengerDetails;
    private DriverDetails driverDetails;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String pickupLocation;
    private String dropoffLocation;
    private Double fare;
    private BookingStatus status;
}
