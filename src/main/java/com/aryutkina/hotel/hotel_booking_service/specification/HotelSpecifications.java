package com.aryutkina.hotel.hotel_booking_service.specification;

import com.aryutkina.hotel.hotel_booking_service.model.Hotel;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class HotelSpecifications {

    public static Specification<Hotel> withFilter(Long id, String name, String title, String city,
                                                  String address, Double minDistance, Double maxDistance) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (city != null && !city.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + city.toLowerCase() + "%"));
            }
            if (address != null && !address.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + address.toLowerCase() + "%"));
            }
            if (minDistance != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("distanceFromCenter"), minDistance));
            }
            if (maxDistance != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("distanceFromCenter"), maxDistance));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}