package com.aryutkina.hotel.hotel_booking_service.service;

import com.aryutkina.hotel.hotel_booking_service.dto.request.RoomCreateRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.request.RoomFilterDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.RoomResponseDto;
import com.aryutkina.hotel.hotel_booking_service.exception.NotFoundException;
import com.aryutkina.hotel.hotel_booking_service.mapper.RoomMapper;
import com.aryutkina.hotel.hotel_booking_service.model.Hotel;
import com.aryutkina.hotel.hotel_booking_service.model.Room;
import com.aryutkina.hotel.hotel_booking_service.repository.HotelRepository;
import com.aryutkina.hotel.hotel_booking_service.repository.RoomRepository;
import com.aryutkina.hotel.hotel_booking_service.specification.RoomSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    @Transactional
    public RoomResponseDto createRoom(RoomCreateRequestDto createDto) {
        Hotel hotel = hotelRepository.findById(createDto.getHotelId())
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + createDto.getHotelId()));

        Room room = roomMapper.toEntity(createDto);
        room.setHotel(hotel);
        Room saved = roomRepository.save(room);
        return roomMapper.toResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public RoomResponseDto getRoomById(Long id) {
        Room room = findRoomById(id);
        return roomMapper.toResponseDto(room);
    }

    @Transactional
    public RoomResponseDto updateRoom(Long id, RoomCreateRequestDto updateDto) {
        Room room = findRoomById(id);

        // Если hotelId изменился, нужно обновить отель
        if (!room.getHotel().getId().equals(updateDto.getHotelId())) {
            Hotel newHotel = hotelRepository.findById(updateDto.getHotelId())
                    .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + updateDto.getHotelId()));
            room.setHotel(newHotel);
        }

        roomMapper.updateEntityFromDto(updateDto, room);
        Room updated = roomRepository.save(room);
        return roomMapper.toResponseDto(updated);
    }

    @Transactional
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new NotFoundException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }

    private Room findRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<RoomResponseDto> filterRooms(RoomFilterDto filterDto) {
        Specification<Room> spec = RoomSpecifications.withFilter(
                filterDto.getId(),
                filterDto.getName(),
                filterDto.getMinPrice(),
                filterDto.getMaxPrice(),
                filterDto.getMaxGuests(),
                filterDto.getCheckIn(),
                filterDto.getCheckOut(),
                filterDto.getHotelId()
        );
        Pageable pageable = filterDto.toPageable();
        return roomRepository.findAll(spec, pageable)
                .map(roomMapper::toResponseDto);
    }
}