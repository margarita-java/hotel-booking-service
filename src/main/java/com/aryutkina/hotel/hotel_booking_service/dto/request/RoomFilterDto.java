package com.aryutkina.hotel.hotel_booking_service.dto.request;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class RoomFilterDto {
    private Long id;
    private String name; // заголовок комнаты
    private Double minPrice;
    private Double maxPrice;
    private Integer maxGuests;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkIn;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOut;
    private Long hotelId;
    private int page = 0;
    private int size = 10;
    private String sortBy = "id";
    private String sortDirection = "asc";

    public Pageable toPageable() {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return PageRequest.of(page, size, sort);
    }
}