package com.przemyslawren.escapethat.controller;

import com.przemyslawren.escapethat.service.BookingService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/generate-slots")
    public ResponseEntity<?> generateDailySlots(@RequestParam Long escapeRoomId,
                                                @RequestParam LocalDate date) {
        bookingService.generateDailySlots(escapeRoomId, date);
        return ResponseEntity.ok("Daily slots generated successfully");
    }
}