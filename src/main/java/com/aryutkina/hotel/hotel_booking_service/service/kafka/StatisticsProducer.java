package com.aryutkina.hotel.hotel_booking_service.service.kafka;

import com.aryutkina.hotel.hotel_booking_service.event.BookingEvent;
import com.aryutkina.hotel.hotel_booking_service.event.UserRegistrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserRegistrationEvent(UserRegistrationEvent event) {
        log.info("Sending user registration event: {}", event);
        kafkaTemplate.send("user-registration", event);
    }

    public void sendBookingEvent(BookingEvent event) {
        log.info("Sending booking event: {}", event);
        kafkaTemplate.send("booking", event);
    }
}