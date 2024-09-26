package com.booking.service.entity;

import com.booking.service.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bookingId;

    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;
    @Column(nullable = false)
    private String pickupLocation;
    @Column(nullable = false)
    private String dropoffLocation;
    @Column
    private Double fare;
    @JoinColumn()
    private BookingStatus bookingStatus;

}
