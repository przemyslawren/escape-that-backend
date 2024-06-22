package com.przemyslawren.escapethat.service;

import com.przemyslawren.escapethat.model.Booking;
import com.przemyslawren.escapethat.model.EscapeRoom;
import com.przemyslawren.escapethat.model.enums.BookingStatus;
import com.przemyslawren.escapethat.repository.BookingRepository;
import com.przemyslawren.escapethat.repository.EscapeRoomRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EscapeRoomRepository escapeRoomRepository;

    private static final List<LocalTime> TIME_SLOTS = List.of(
            LocalTime.of(9, 0),
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0),
            LocalTime.of(16, 0),
            LocalTime.of(17, 0),
            LocalTime.of(18, 0),
            LocalTime.of(19, 0),
            LocalTime.of(20, 0),
            LocalTime.of(21, 0)
    );

    @Transactional
    public void generateDailySlots(Long escapeRoomId, LocalDate date) {
        EscapeRoom escapeRoom = escapeRoomRepository.findById(escapeRoomId)
                .orElseThrow(() -> new IllegalArgumentException("EscapeRoom not found"));

        int slotNumber = 1;
        for (LocalTime timeSlot : TIME_SLOTS) {
            LocalDateTime startTime = LocalDateTime.of(date, timeSlot);

            if (!bookingRepository.existsByEscapeRoomAndStartTime(escapeRoom, startTime)) {
                Booking booking = new Booking();
                booking.setEscapeRoom(escapeRoom);
                booking.setStartTime(startTime);
                booking.setSlotNumber(slotNumber++);
                booking.setStatus(BookingStatus.PENDING);

                bookingRepository.save(booking);
            }
        }
    }
}