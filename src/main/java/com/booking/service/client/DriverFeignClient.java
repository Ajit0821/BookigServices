package com.booking.service.client;

import com.booking.service.dto.DriverDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "http://DRIVER-SERVICE/v1/drivers-details")
public interface DriverFeignClient {
    @PutMapping("/{driverId}")
    ResponseEntity<DriverDetails> updateDriverStatus(@PathVariable("driverId") Long driverId, @RequestBody DriverDetails driverDetails);
}