package com.aryutkina.hotel.hotel_booking_service.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class BookingRequestDto {

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Check-in date is required")
    @Future(message = "Check-in date must be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    @Future(message = "Check-out date must be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOutDate;
}