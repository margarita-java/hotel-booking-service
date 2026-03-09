package com.aryutkina.hotel.hotel_booking_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class HotelListResponseDto {
    private List<HotelResponseDto> hotels;
    private long totalElements;
}