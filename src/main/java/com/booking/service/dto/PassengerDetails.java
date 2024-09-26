package com.booking.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDetails {

    private Long passengerId;

    private String passengerName;

    private  String passengerContactNumber;
}
