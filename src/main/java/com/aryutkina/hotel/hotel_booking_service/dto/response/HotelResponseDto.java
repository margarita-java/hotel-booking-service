package com.aryutkina.hotel.hotel_booking_service.dto.response;

import lombok.Data;

@Data
public class HotelResponseDto {
    private Long id;
    private String name;
    private String title;
    private String city;
    private String address;
    private Double distanceFromCenter;
    private Double rating;
    private Integer numberOfRatings;
}