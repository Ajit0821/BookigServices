package com.booking.service.service;

import com.booking.service.client.DriverFeignClient;
import com.booking.service.dto.BookingDetailsDto;
import com.booking.service.dto.DriverDetails;
import com.booking.service.dto.DriversDetailsResponse;
import com.booking.service.dto.PassengerDetails;
import com.booking.service.entity.BookingDetails;
import com.booking.service.exception.DriverNotFoundException;
import com.booking.service.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ProducerKafka producerKafka;

    @Autowired
    private DriverFeignClient driverFeignClient;
//    @Autowired
//    private EmailService emailService;
    List<DriverDetails> driverDetailsList;


    private final RestTemplate restTemplate;

    public BookingServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BookingDetailsDto saveBookingDetails(BookingDetails bookingDetails, Long passengerId) throws Exception {
        String passengerServiceUrl = "http://PASSENGER-SERVICE/v1/passenger-details/" + passengerId;
        String driverServiceUrl = "http://DRIVER-SERVICE/v1/drivers-details";
        ResponseEntity<PassengerDetails> response = restTemplate
                .getForEntity(passengerServiceUrl, PassengerDetails.class);
        PassengerDetails passengerDetails;
        if (response.getStatusCode().is2xxSuccessful()) {
            passengerDetails = response.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve passenger details");
        }
        ResponseEntity<DriversDetailsResponse> driverResponse = restTemplate.exchange(
                driverServiceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        if (response.getStatusCode().is2xxSuccessful()) {
            driverDetailsList = Objects.requireNonNull(driverResponse.getBody()).getDriverDetailsResponse();
        } else {
            throw new RuntimeException("Failed to retrieve passenger details");
        }
        bookingDetails.setStartTime(LocalDateTime.now());
        bookingDetails.setEndTime(LocalDateTime.now());
        BookingDetails savedBookingDetails = bookingRepository.save(bookingDetails);
        BookingDetailsDto bookingDetailsDto = new BookingDetailsDto();
        bookingDetailsDto.setDriverDetails(getDriverDetails(savedBookingDetails.getPickupLocation()));
        bookingDetailsDto.setPassengerDetails(passengerDetails);
        bookingDetailsDto.setStartTime(LocalDateTime.now());
        bookingDetailsDto.setEndTime(LocalDateTime.now());
        bookingDetailsDto.setBookingId(savedBookingDetails.getBookingId());
        bookingDetailsDto.setStatus(savedBookingDetails.getBookingStatus());
        bookingDetailsDto.setFare(savedBookingDetails.getFare());
        bookingDetailsDto.setDropoffLocation(bookingDetails.getDropoffLocation());
        bookingDetailsDto.setPickupLocation(bookingDetails.getPickupLocation());

//        emailService.sendSimpleEmail("abhiofficial.1599@gmail.com", "Ride confirmation", "Your ride is confirmed!");
        producerKafka.sendBookingEvent(bookingDetails);
        return bookingDetailsDto;
    }

    @Override
    public BookingDetailsDto getBookingDetails(Long bookingId) throws DriverNotFoundException {
        BookingDetails bookingDetails = bookingRepository.findById(bookingId).orElseThrow(()->new DriverNotFoundException("Passenger Not found"));
        return modelMapper.map(bookingDetails,BookingDetailsDto.class);
    }

    private DriverDetails getDriverDetails(String pickupLocation) throws Exception {
        DriverDetails driverDetails;
        driverDetails =  driverDetailsList.stream()
//                .filter(d->
//                pickupLocation.equalsIgnoreCase(d.getDriverCurrentLocation()) )
//                        && d.isAvailability_Status())
                        .findAny().orElseThrow(()->new DriverNotFoundException("No Driver Available"));

//        driverDetails.setAvailability_Status(false);
        ResponseEntity<DriverDetails> response = driverFeignClient.updateDriverStatus(driverDetails.getDriverId(),driverDetails);
        return  driverDetails;
    }

}
