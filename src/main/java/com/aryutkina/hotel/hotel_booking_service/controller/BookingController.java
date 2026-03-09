package com.aryutkina.hotel.hotel_booking_service.controller;

import com.aryutkina.hotel.hotel_booking_service.dto.request.BookingRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.BookingResponseDto;
import com.aryutkina.hotel.hotel_booking_service.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@Valid @RequestBody BookingRequestDto requestDto) {
        return new ResponseEntity<>(bookingService.createBooking(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<BookingResponseDto>> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(bookingService.getAllBookings(pageable));
    }
}