package com.aryutkina.hotel.hotel_booking_service.service.statistics;

import com.aryutkina.hotel.hotel_booking_service.model.statistics.StatisticsEvent;
import com.aryutkina.hotel.hotel_booking_service.repository.statistics.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public void saveEvent(StatisticsEvent event) {
        statisticsRepository.save(event);
    }

    public List<StatisticsEvent> getAllEvents() {
        return statisticsRepository.findAll();
    }
}