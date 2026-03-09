package com.aryutkina.hotel.hotel_booking_service.specification;

import com.aryutkina.hotel.hotel_booking_service.model.Booking;
import com.aryutkina.hotel.hotel_booking_service.model.Room;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomSpecifications {

    public static Specification<Room> withFilter(Long id, String name, Double minPrice, Double maxPrice,
                                                 Integer maxGuests, LocalDate checkIn, LocalDate checkOut,
                                                 Long hotelId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            if (maxGuests != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxGuests"), maxGuests));
            }
            if (hotelId != null) {
                predicates.add(criteriaBuilder.equal(root.get("hotel").get("id"), hotelId));
            }

            // Фильтрация по датам: если указаны обе даты
            if (checkIn != null && checkOut != null) {
                // Проверяем, что комната свободна в этом диапазоне (нет пересекающихся бронирований)
                Subquery<Long> bookingSubquery = query.subquery(Long.class);
                Root<Booking> bookingRoot = bookingSubquery.from(Booking.class);
                bookingSubquery.select(bookingRoot.get("id"));

                Predicate sameRoom = criteriaBuilder.equal(bookingRoot.get("room"), root);
                Predicate overlap = criteriaBuilder.and(
                        criteriaBuilder.lessThan(bookingRoot.get("checkInDate"), checkOut),
                        criteriaBuilder.greaterThan(bookingRoot.get("checkOutDate"), checkIn)
                );
                bookingSubquery.where(criteriaBuilder.and(sameRoom, overlap));

                // Комната подходит, если нет пересекающихся бронирований
                predicates.add(criteriaBuilder.not(criteriaBuilder.exists(bookingSubquery)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}