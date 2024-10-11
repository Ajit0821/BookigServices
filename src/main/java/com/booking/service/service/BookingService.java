package com.booking.service.service;

import com.booking.service.dto.BookingDetailsDto;
import com.booking.service.entity.BookingDetails;
import com.booking.service.exception.DriverNotFoundException;

public interface BookingService {
    BookingDetailsDto saveBookingDetails(BookingDetails bookingDetails, Long passengerId) throws Exception;

    BookingDetailsDto getBookingDetails(Long passengerId) throws DriverNotFoundException;
}
