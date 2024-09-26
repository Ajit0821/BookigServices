package com.booking.service.controller;

import com.booking.service.dto.BookingDetailsDto;
import com.booking.service.entity.BookingDetails;
import com.booking.service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class Controller {

    @Autowired
    private BookingService bookingService;
    @PostMapping("/book-ride/{passengerId}")
    public ResponseEntity<BookingDetailsDto> bookRide(@RequestBody BookingDetails bookingDetails,
                                                      @PathVariable Long passengerId) throws Exception {
        BookingDetailsDto bookingDetailsDto = bookingService.saveBookingDetails(bookingDetails, passengerId);
        return new ResponseEntity<>(bookingDetailsDto, HttpStatus.CREATED);
    }
}
