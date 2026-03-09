package com.aryutkina.hotel.hotel_booking_service.controller;

import com.aryutkina.hotel.hotel_booking_service.dto.request.RoomCreateRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.request.RoomFilterDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.RoomResponseDto;
import com.aryutkina.hotel.hotel_booking_service.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(@Valid @RequestBody RoomCreateRequestDto createDto) {
        return new ResponseEntity<>(roomService.createRoom(createDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDto> updateRoom(@PathVariable Long id,
                                                      @Valid @RequestBody RoomCreateRequestDto updateDto) {
        return ResponseEntity.ok(roomService.updateRoom(id, updateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<RoomResponseDto>> filterRooms(RoomFilterDto filterDto) {
        return ResponseEntity.ok(roomService.filterRooms(filterDto));
    }
}