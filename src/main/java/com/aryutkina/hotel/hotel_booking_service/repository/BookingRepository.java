package com.aryutkina.hotel.hotel_booking_service.repository;

import com.aryutkina.hotel.hotel_booking_service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Найти все бронирования для конкретной комнаты, которые пересекаются с заданным интервалом
    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
            "AND b.checkInDate < :checkOut AND b.checkOutDate > :checkIn")
    List<Booking> findOverlappingBookings(@Param("roomId") Long roomId,
                                          @Param("checkIn") LocalDate checkIn,
                                          @Param("checkOut") LocalDate checkOut);

    // Получить все бронирования (для администратора)
    List<Booking> findAll();
}