package com.booking.service.service;

import com.booking.service.entity.BookingDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerKafka {
    private static final String TOPIC = "ride-events";

    @Autowired
    private KafkaTemplate<String, BookingDetails> kafkaTemplate;
    public void sendBookingEvent(BookingDetails bookingDetails) {
        kafkaTemplate.send(TOPIC, bookingDetails);
    }
}
