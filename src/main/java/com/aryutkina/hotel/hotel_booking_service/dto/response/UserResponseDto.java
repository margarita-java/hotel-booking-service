package com.aryutkina.hotel.hotel_booking_service.dto.response;

import com.aryutkina.hotel.hotel_booking_service.model.Role;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
}