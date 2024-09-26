package com.booking.service.service;

import com.booking.service.dto.BookingDetailsDto;
import com.booking.service.entity.BookingDetails;

public interface BookingService {
    BookingDetailsDto saveBookingDetails(BookingDetails bookingDetails, Long passengerId) throws Exception;
}
