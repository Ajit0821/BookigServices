package com.booking.service.repository;

import com.booking.service.entity.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingDetails,Long> {
}
