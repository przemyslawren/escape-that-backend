package com.przemyslawren.escapethat.dto;

import com.przemyslawren.escapethat.model.enums.BookingStatus;
import java.time.LocalDateTime;

public record BookingRequestDto(
        BookingStatus status,
        Long customerId,
        Long escapeRoomId,
        LocalDateTime startTime,
        int slotNumber,
        boolean promoCode
) {
}
