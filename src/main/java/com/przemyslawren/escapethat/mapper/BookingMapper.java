package com.przemyslawren.escapethat.mapper;

import com.przemyslawren.escapethat.dto.BookingDto;
import com.przemyslawren.escapethat.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingDto toDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStatus(),
                booking.getStartTime(),
                booking.getSlotNumber(),
                booking.isPromoCode()
        );
    }

    public Booking toEntity(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.id());
        booking.setStatus(bookingDto.status());
        booking.setStartTime(bookingDto.startTime());
        booking.setSlotNumber(bookingDto.slotNumber());
        booking.setPromoCode(bookingDto.promoCode());
        return booking;
    }
}
