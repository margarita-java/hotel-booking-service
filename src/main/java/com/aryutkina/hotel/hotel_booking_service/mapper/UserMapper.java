package com.aryutkina.hotel.hotel_booking_service.mapper;

import com.aryutkina.hotel.hotel_booking_service.dto.request.UserCreateRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.UserResponseDto;
import com.aryutkina.hotel.hotel_booking_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserCreateRequestDto dto);

    UserResponseDto toResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(UserCreateRequestDto dto, @MappingTarget User user);
}