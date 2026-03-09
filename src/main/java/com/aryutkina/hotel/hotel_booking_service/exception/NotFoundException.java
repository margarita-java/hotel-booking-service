package com.aryutkina.hotel.hotel_booking_service.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}