package com.przemyslawren.escapethat.controller;

import com.przemyslawren.escapethat.dto.BookingDto;
import com.przemyslawren.escapethat.dto.BookingRequestDto;
import com.przemyslawren.escapethat.service.EscapeRoomBookingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/escape-rooms")
@RequiredArgsConstructor
public class EscapeRoomBookingController {

    private final EscapeRoomBookingService escapeRoomBookingService;

    @PostMapping("/{id}/create-booking")
    public void createBooking(@PathVariable("id") Long escapeRoomId,
                                    @RequestBody BookingRequestDto bookingRequestDto) {

        escapeRoomBookingService.addBooking(escapeRoomId, bookingRequestDto);
    }

    @GetMapping("/{id}/")
    public List<BookingDto> bookings(@PathVariable("id") Long escapeRoomId) {
       return escapeRoomBookingService.getBookingsByEscapeRoomId(escapeRoomId);
    }
}
