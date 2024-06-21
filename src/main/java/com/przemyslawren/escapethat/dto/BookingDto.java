package com.przemyslawren.escapethat.dto;

import com.przemyslawren.escapethat.model.enums.BookingStatus;
import java.time.LocalDateTime;

public record BookingDto(
    Long id,
    BookingStatus status,
    LocalDateTime startTime,
    int slotNumber,
    boolean promoCode,
    EscapeRoomSimpleDto escapeRoomSimpleDto
) {
}
