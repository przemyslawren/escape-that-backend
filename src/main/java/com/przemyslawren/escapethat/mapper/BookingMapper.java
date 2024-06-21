package com.przemyslawren.escapethat.mapper;

import com.przemyslawren.escapethat.dto.AddressDto;
import com.przemyslawren.escapethat.dto.BookingDto;
import com.przemyslawren.escapethat.dto.EscapeRoomSimpleDto;
import com.przemyslawren.escapethat.dto.PlayerRangeDto;
import com.przemyslawren.escapethat.dto.RoomThemeDto;
import com.przemyslawren.escapethat.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingDto toDto(Booking booking) {
        AddressDto addressDto = new AddressDto(
                booking.getEscapeRoom().getAddress().getCity(),
                booking.getEscapeRoom().getAddress().getStreet(),
                booking.getEscapeRoom().getAddress().getPostalCode()
        );
        RoomThemeDto roomThemeDto = new RoomThemeDto(
                booking.getEscapeRoom().getRoomTheme().getThemeName(),
                booking.getEscapeRoom().getRoomTheme().getDescription()
        );
        EscapeRoomSimpleDto escapeRoomSimpleDto =
                getEscapeRoomSimpleDto(booking, addressDto, roomThemeDto);


        return new BookingDto(
                booking.getId(),
                booking.getStatus(),
                booking.getStartTime(),
                booking.getSlotNumber(),
                booking.isPromoCode(),
                escapeRoomSimpleDto
        );
    }

    private static EscapeRoomSimpleDto getEscapeRoomSimpleDto(Booking booking,
                                                              AddressDto addressDto,
                                                              RoomThemeDto roomThemeDto) {
        PlayerRangeDto playerRangeDto = new PlayerRangeDto(
                booking.getEscapeRoom().getPlayerRange().getMinPlayers(),
                booking.getEscapeRoom().getPlayerRange().getMaxPlayers()
        );

        return new EscapeRoomSimpleDto(
                booking.getEscapeRoom().getId(),
                booking.getEscapeRoom().getName(),
                booking.getEscapeRoom().getDifficultyLevel(),
                booking.getEscapeRoom().isHasActor(),
                addressDto,
                roomThemeDto,
                playerRangeDto,
                booking.getEscapeRoom().getBasePrice()
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
