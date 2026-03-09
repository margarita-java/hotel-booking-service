package com.aryutkina.hotel.hotel_booking_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Double price;

    @Column(name = "max_guests", nullable = false)
    private Integer maxGuests;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    // Даты, когда комната недоступна (например, ремонт)
    @ElementCollection
    @CollectionTable(name = "room_unavailable_dates",
            joinColumns = @JoinColumn(name = "room_id"))
    private List<DateRange> unavailableDates = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();
}