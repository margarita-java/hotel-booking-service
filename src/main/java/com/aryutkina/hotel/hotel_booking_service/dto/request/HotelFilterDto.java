package com.aryutkina.hotel.hotel_booking_service.dto.request;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class HotelFilterDto {
    private Long id;
    private String name;
    private String title;
    private String city;
    private String address;
    private Double minDistance;
    private Double maxDistance;
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