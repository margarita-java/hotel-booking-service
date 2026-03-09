package com.aryutkina.hotel.hotel_booking_service.repository.statistics;

import com.aryutkina.hotel.hotel_booking_service.model.statistics.StatisticsEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends MongoRepository<StatisticsEvent, String> {
}