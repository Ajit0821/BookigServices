package com.booking.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalException {
    String currentTime= LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

    @ExceptionHandler(DriverNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> driverNotFoundException(DriverNotFoundException driverNotFoundException){
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setMessage(driverNotFoundException.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimestamp(currentTime);
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

}
