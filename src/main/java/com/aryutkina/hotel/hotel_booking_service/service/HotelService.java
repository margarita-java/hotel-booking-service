package com.aryutkina.hotel.hotel_booking_service.service;

import com.aryutkina.hotel.hotel_booking_service.dto.request.HotelCreateRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.request.HotelFilterDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.HotelResponseDto;
import com.aryutkina.hotel.hotel_booking_service.exception.NotFoundException;
import com.aryutkina.hotel.hotel_booking_service.mapper.HotelMapper;
import com.aryutkina.hotel.hotel_booking_service.model.Hotel;
import com.aryutkina.hotel.hotel_booking_service.repository.HotelRepository;
import com.aryutkina.hotel.hotel_booking_service.specification.HotelSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Transactional
    public HotelResponseDto createHotel(HotelCreateRequestDto createDto) {
        Hotel hotel = hotelMapper.toEntity(createDto);
        hotel.setRating(0.0);
        hotel.setNumberOfRatings(0);
        Hotel saved = hotelRepository.save(hotel);
        return hotelMapper.toResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public HotelResponseDto getHotelById(Long id) {
        Hotel hotel = findHotelById(id);
        return hotelMapper.toResponseDto(hotel);
    }

    @Transactional
    public HotelResponseDto updateHotel(Long id, HotelCreateRequestDto updateDto) {
        Hotel hotel = findHotelById(id);
        hotelMapper.updateEntityFromDto(updateDto, hotel);
        Hotel updated = hotelRepository.save(hotel);
        return hotelMapper.toResponseDto(updated);
    }

    @Transactional
    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new NotFoundException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<HotelResponseDto> getAllHotels(Pageable pageable) {
        return hotelRepository.findAll(pageable)
                .map(hotelMapper::toResponseDto);
    }

    private Hotel findHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));
    }

    @Transactional
    public HotelResponseDto rateHotel(Long id, Integer newMark) {
        Hotel hotel = findHotelById(id);

        if (hotel.getNumberOfRatings() == 0) {
            // Первая оценка
            hotel.setRating(newMark.doubleValue());
            hotel.setNumberOfRatings(1);
        } else {
            double currentRating = hotel.getRating();
            int numberOfRatings = hotel.getNumberOfRatings();

            // Формула из ТЗ
            double totalRating = currentRating * numberOfRatings;
            totalRating = totalRating - currentRating + newMark;
            double newRating = totalRating / numberOfRatings;
            // Округление до одного знака после запятой
            newRating = Math.round(newRating * 10) / 10.0;

            hotel.setNumberOfRatings(numberOfRatings + 1);
            hotel.setRating(newRating);
        }

        Hotel saved = hotelRepository.save(hotel);
        return hotelMapper.toResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<HotelResponseDto> filterHotels(HotelFilterDto filterDto) {
        Specification<Hotel> spec = HotelSpecifications.withFilter(
                filterDto.getId(),
                filterDto.getName(),
                filterDto.getTitle(),
                filterDto.getCity(),
                filterDto.getAddress(),
                filterDto.getMinDistance(),
                filterDto.getMaxDistance()
        );
        Pageable pageable = filterDto.toPageable();
        return hotelRepository.findAll(spec, pageable)
                .map(hotelMapper::toResponseDto);
    }
}