package com.aryutkina.hotel.hotel_booking_service.repository;

import com.aryutkina.hotel.hotel_booking_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // проверка существования пользователя с указанными именем или email
    default boolean existsByUsernameOrEmail(String username, String email) {
        return existsByUsername(username) || existsByEmail(email);
    }
}