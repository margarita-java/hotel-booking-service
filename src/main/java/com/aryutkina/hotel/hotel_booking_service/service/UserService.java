package com.aryutkina.hotel.hotel_booking_service.service;

import com.aryutkina.hotel.hotel_booking_service.dto.request.UserCreateRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.UserResponseDto;
import com.aryutkina.hotel.hotel_booking_service.event.UserRegistrationEvent;
import com.aryutkina.hotel.hotel_booking_service.exception.NotFoundException;
import com.aryutkina.hotel.hotel_booking_service.mapper.UserMapper;
import com.aryutkina.hotel.hotel_booking_service.model.User;
import com.aryutkina.hotel.hotel_booking_service.repository.UserRepository;
import com.aryutkina.hotel.hotel_booking_service.service.kafka.StatisticsProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final StatisticsProducer statisticsProducer;

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto createDto) {
        if (userRepository.existsByUsernameOrEmail(createDto.getUsername(), createDto.getEmail())) {
            throw new IllegalArgumentException("User with this username or email already exists");
        }

        User user = userMapper.toEntity(createDto);
        user.setPassword(passwordEncoder.encode(createDto.getPassword()));
        User saved = userRepository.save(user);

        // Отправка события регистрации
        UserRegistrationEvent event = new UserRegistrationEvent(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                LocalDateTime.now()
        );
        statisticsProducer.sendUserRegistrationEvent(event);

        return userMapper.toResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = findUserById(id);
        return userMapper.toResponseDto(user);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }
}