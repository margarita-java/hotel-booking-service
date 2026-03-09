package com.aryutkina.hotel.hotel_booking_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class HotelCreateRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Distance from center is required")
    @Positive(message = "Distance must be positive")
    private Double distanceFromCenter;
}