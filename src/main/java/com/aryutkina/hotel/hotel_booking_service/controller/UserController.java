package com.aryutkina.hotel.hotel_booking_service.controller;

import com.aryutkina.hotel.hotel_booking_service.dto.request.UserCreateRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.UserResponseDto;
import com.aryutkina.hotel.hotel_booking_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto createDto) {
        return new ResponseEntity<>(userService.createUser(createDto), HttpStatus.CREATED);
    }

    // Дополнительно можно добавить метод получения пользователя по ID (для тестирования)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}