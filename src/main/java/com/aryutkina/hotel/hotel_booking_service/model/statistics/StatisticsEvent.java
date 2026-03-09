package com.aryutkina.hotel.hotel_booking_service.model.statistics;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(collection = "statistics")
public class StatisticsEvent {
    @Id
    private String id;
    private String eventType; // "REGISTRATION" или "BOOKING"
    private Long userId;
    private String username;   // для регистрации
    private String email;      // для регистрации
    private Long bookingId;    // для бронирования
    private Long roomId;       // для бронирования
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime timestamp;
}