package com.przemyslawren.escapethat.controller;

import com.przemyslawren.escapethat.model.Booking;
import com.przemyslawren.escapethat.service.BookingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/escape-room/{escapeRoomId}/slot/{slotNumber}/start-time")
    public ResponseEntity<Booking> getBookingByEscapeRoomIdAndSlotNumberAndStartTime(@PathVariable Long escapeRoomId, @PathVariable int slotNumber, @RequestParam LocalDateTime startTime) {
        return bookingService.findBookingByEscapeRoomIdAndSlotNumberAndStartTime(escapeRoomId, slotNumber, startTime)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/escape-room/{escapeRoomId}/slot/{slotNumber}/date/{date}")
    public ResponseEntity<List<Booking>> getBookingsBySlotNumberAndDate(@PathVariable Long escapeRoomId, @PathVariable int slotNumber, @PathVariable
    LocalDate date) {
        List<Booking> bookings = bookingService.findBookingsBySlotNumberAndDate(escapeRoomId, slotNumber, date);
        if (bookings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/generate-slots")
    public ResponseEntity<?> generateDailySlots(@RequestParam Long escapeRoomId, @RequestParam LocalDate date) {
        bookingService.generateDailySlots(escapeRoomId, date);
        return ResponseEntity.ok("Daily slots generated successfully");
    }
}