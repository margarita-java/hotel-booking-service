package com.aryutkina.hotel.hotel_booking_service.mapper;

import com.aryutkina.hotel.hotel_booking_service.dto.request.BookingRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.BookingResponseDto;
import com.aryutkina.hotel.hotel_booking_service.model.Booking;
import com.aryutkina.hotel.hotel_booking_service.model.Room;
import com.aryutkina.hotel.hotel_booking_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)  // устанавливается в сервисе
    @Mapping(target = "user", ignore = true)  // устанавливается в сервисе
    Booking toEntity(BookingRequestDto dto);

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "roomName", source = "room.name")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    BookingResponseDto toResponseDto(Booking booking);
}