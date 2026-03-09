package com.aryutkina.hotel.hotel_booking_service.mapper;

import com.aryutkina.hotel.hotel_booking_service.dto.request.HotelCreateRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.HotelResponseDto;
import com.aryutkina.hotel.hotel_booking_service.model.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "numberOfRatings", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    Hotel toEntity(HotelCreateRequestDto dto);

    HotelResponseDto toResponseDto(Hotel hotel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "numberOfRatings", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    void updateEntityFromDto(HotelCreateRequestDto dto, @MappingTarget Hotel hotel);
}