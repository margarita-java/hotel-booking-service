package com.aryutkina.hotel.hotel_booking_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}