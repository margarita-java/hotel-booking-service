package com.aryutkina.hotel.hotel_booking_service.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRange {
    private LocalDate startDate;
    private LocalDate endDate;
}