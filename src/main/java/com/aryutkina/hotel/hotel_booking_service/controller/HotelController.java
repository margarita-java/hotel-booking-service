package com.aryutkina.hotel.hotel_booking_service.controller;

import com.aryutkina.hotel.hotel_booking_service.dto.request.HotelCreateRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.request.HotelFilterDto;
import com.aryutkina.hotel.hotel_booking_service.dto.request.RatingRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.HotelListResponseDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.HotelResponseDto;
import com.aryutkina.hotel.hotel_booking_service.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDto> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @PostMapping
    public ResponseEntity<HotelResponseDto> createHotel(@Valid @RequestBody HotelCreateRequestDto createDto) {
        return new ResponseEntity<>(hotelService.createHotel(createDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponseDto> updateHotel(@PathVariable Long id,
                                                        @Valid @RequestBody HotelCreateRequestDto updateDto) {
        return ResponseEntity.ok(hotelService.updateHotel(id, updateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<HotelListResponseDto> getAllHotels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<HotelResponseDto> hotelPage = hotelService.getAllHotels(pageable);

        HotelListResponseDto response = new HotelListResponseDto(
                hotelPage.getContent(),
                hotelPage.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/rating")
    public ResponseEntity<HotelResponseDto> rateHotel(@PathVariable Long id,
                                                      @Valid @RequestBody RatingRequestDto ratingDto) {
        return ResponseEntity.ok(hotelService.rateHotel(id, ratingDto.getMark()));
    }

    @GetMapping("/filter")
    public ResponseEntity<HotelListResponseDto> filterHotels(HotelFilterDto filterDto) {
        Page<HotelResponseDto> hotelPage = hotelService.filterHotels(filterDto);
        HotelListResponseDto response = new HotelListResponseDto(
                hotelPage.getContent(),
                hotelPage.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }
}