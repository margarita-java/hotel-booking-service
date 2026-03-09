package com.aryutkina.hotel.hotel_booking_service.service.kafka;

import com.aryutkina.hotel.hotel_booking_service.event.BookingEvent;
import com.aryutkina.hotel.hotel_booking_service.event.UserRegistrationEvent;
import com.aryutkina.hotel.hotel_booking_service.model.statistics.StatisticsEvent;
import com.aryutkina.hotel.hotel_booking_service.service.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsConsumer {

    private final StatisticsService statisticsService;

    @KafkaListener(topics = "user-registration", groupId = "hotel-group")
    public void handleUserRegistration(UserRegistrationEvent event) {
        log.info("Received user registration event: {}", event);
        StatisticsEvent stats = new StatisticsEvent();
        stats.setEventType("REGISTRATION");
        stats.setUserId(event.getUserId());
        stats.setUsername(event.getUsername());
        stats.setEmail(event.getEmail());
        stats.setTimestamp(event.getTimestamp());
        statisticsService.saveEvent(stats);
    }

    @KafkaListener(topics = "booking", groupId = "hotel-group")
    public void handleBooking(BookingEvent event) {
        log.info("Received booking event: {}", event);
        StatisticsEvent stats = new StatisticsEvent();
        stats.setEventType("BOOKING");
        stats.setUserId(event.getUserId());
        stats.setBookingId(event.getBookingId());
        stats.setRoomId(event.getRoomId());
        stats.setCheckInDate(event.getCheckInDate());
        stats.setCheckOutDate(event.getCheckOutDate());
        stats.setTimestamp(event.getTimestamp());
        statisticsService.saveEvent(stats);
    }
}