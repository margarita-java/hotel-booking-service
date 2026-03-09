package com.aryutkina.hotel.hotel_booking_service.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingRequestDto {
    @NotNull(message = "Mark is required")
    @Min(value = 1, message = "Mark must be at least 1")
    @Max(value = 5, message = "Mark must be at most 5")
    private Integer mark;
}