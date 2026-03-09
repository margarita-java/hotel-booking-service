package com.aryutkina.hotel.hotel_booking_service.controller;

import com.aryutkina.hotel.hotel_booking_service.model.statistics.StatisticsEvent;
import com.aryutkina.hotel.hotel_booking_service.service.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/export/csv")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> exportCsv() {
        List<StatisticsEvent> events = statisticsService.getAllEvents();

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        // Заголовки CSV
        writer.println("id,eventType,userId,username,email,bookingId,roomId,checkInDate,checkOutDate,timestamp");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        for (StatisticsEvent event : events) {
            writer.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                    escapeCsv(event.getId()),
                    escapeCsv(event.getEventType()),
                    event.getUserId() != null ? event.getUserId() : "",
                    escapeCsv(event.getUsername()),
                    escapeCsv(event.getEmail()),
                    event.getBookingId() != null ? event.getBookingId() : "",
                    event.getRoomId() != null ? event.getRoomId() : "",
                    event.getCheckInDate() != null ? event.getCheckInDate().format(dateFormatter) : "",
                    event.getCheckOutDate() != null ? event.getCheckOutDate().format(dateFormatter) : "",
                    event.getTimestamp() != null ? event.getTimestamp().format(dateTimeFormatter) : ""
            );
        }

        String csvContent = stringWriter.toString();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statistics.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csvContent);
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        return value;
    }
}