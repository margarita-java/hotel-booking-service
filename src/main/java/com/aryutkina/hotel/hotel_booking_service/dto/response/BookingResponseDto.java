package com.aryutkina.hotel.hotel_booking_service.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingResponseDto {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long roomId;
    private String roomName;
    private Long userId;
    private String username;
}