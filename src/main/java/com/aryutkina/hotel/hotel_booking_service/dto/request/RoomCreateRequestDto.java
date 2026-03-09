package com.aryutkina.hotel.hotel_booking_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class RoomCreateRequestDto {

    @NotBlank(message = "Room name is required")
    private String name;

    private String description;

    @NotBlank(message = "Room number is required")
    private String number;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Max guests is required")
    @Min(value = 1, message = "Max guests must be at least 1")
    private Integer maxGuests;

    @NotNull(message = "Hotel ID is required")
    private Long hotelId;

    // Опционально: можно передавать периоды недоступности при создании/редактировании
    private List<DateRangeDto> unavailableDates;

    @Data
    public static class DateRangeDto {
        @NotNull
        private String startDate; // формат yyyy-MM-dd
        @NotNull
        private String endDate;
    }
}