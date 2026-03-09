package com.aryutkina.hotel.hotel_booking_service.service;

import com.aryutkina.hotel.hotel_booking_service.dto.request.BookingRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.BookingResponseDto;
import com.aryutkina.hotel.hotel_booking_service.event.BookingEvent;
import com.aryutkina.hotel.hotel_booking_service.exception.NotFoundException;
import com.aryutkina.hotel.hotel_booking_service.mapper.BookingMapper;
import com.aryutkina.hotel.hotel_booking_service.model.Booking;
import com.aryutkina.hotel.hotel_booking_service.model.DateRange;
import com.aryutkina.hotel.hotel_booking_service.model.Room;
import com.aryutkina.hotel.hotel_booking_service.model.User;
import com.aryutkina.hotel.hotel_booking_service.repository.BookingRepository;
import com.aryutkina.hotel.hotel_booking_service.repository.RoomRepository;
import com.aryutkina.hotel.hotel_booking_service.repository.UserRepository;
import com.aryutkina.hotel.hotel_booking_service.service.kafka.StatisticsProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final StatisticsProducer statisticsProducer;

    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto requestDto) {
        // Проверка существования комнаты
        Room room = roomRepository.findById(requestDto.getRoomId())
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + requestDto.getRoomId()));

        // Проверка существования пользователя
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + requestDto.getUserId()));

        // Проверка корректности дат (checkIn < checkOut)
        if (!requestDto.getCheckInDate().isBefore(requestDto.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }

        // Проверка доступности комнаты на эти даты (не пересекается с другими бронированиями)
        List<Booking> overlapping = bookingRepository.findOverlappingBookings(
                room.getId(), requestDto.getCheckInDate(), requestDto.getCheckOutDate());
        if (!overlapping.isEmpty()) {
            throw new IllegalArgumentException("Room is already booked for the selected dates");
        }

        // Проверка на пересечение с недоступными датами комнаты (unavailableDates)
        boolean unavailable = room.getUnavailableDates().stream()
                .anyMatch(range -> datesOverlap(range, requestDto.getCheckInDate(), requestDto.getCheckOutDate()));
        if (unavailable) {
            throw new IllegalArgumentException("Room is unavailable for the selected dates due to maintenance or other reasons");
        }

        // Создание бронирования
        Booking booking = bookingMapper.toEntity(requestDto);
        booking.setRoom(room);
        booking.setUser(user);
        Booking saved = bookingRepository.save(booking);

        // Отправка события бронирования
        BookingEvent event = new BookingEvent(
                saved.getId(),
                saved.getUser().getId(),
                saved.getRoom().getId(),
                saved.getCheckInDate(),
                saved.getCheckOutDate(),
                LocalDateTime.now()
        );
        statisticsProducer.sendBookingEvent(event);

        return bookingMapper.toResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<BookingResponseDto> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable)
                .map(bookingMapper::toResponseDto);
    }

    // Вспомогательный метод для проверки пересечения двух интервалов
    private boolean datesOverlap(DateRange range, LocalDate checkIn, LocalDate checkOut) {
        return range.getStartDate().isBefore(checkOut) && range.getEndDate().isAfter(checkIn);
    }
}