package com.aryutkina.hotel.hotel_booking_service.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class RoomResponseDto {
    private Long id;
    private String name;
    private String description;
    private String number;
    private Double price;
    private Integer maxGuests;
    private Long hotelId; // только ID отеля для избежания циклической зависимости
    private String hotelName; // можно добавить для удобства
    private List<DateRangeDto> unavailableDates;

    @Data
    public static class DateRangeDto {
        private String startDate;
        private String endDate;
    }
}