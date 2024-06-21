package com.przemyslawren.escapethat.service;

import com.przemyslawren.escapethat.dto.BookingDto;
import com.przemyslawren.escapethat.dto.BookingRequestDto;
import com.przemyslawren.escapethat.dto.CustomerDto;
import com.przemyslawren.escapethat.exception.ErrorCode;
import com.przemyslawren.escapethat.exception.EscapeRoomNotFoundException;
import com.przemyslawren.escapethat.exception.EscapeRoomRuntimeException;
import com.przemyslawren.escapethat.mapper.BookingMapper;
import com.przemyslawren.escapethat.model.Booking;
import com.przemyslawren.escapethat.model.Customer;
import com.przemyslawren.escapethat.model.EscapeRoom;
import com.przemyslawren.escapethat.model.enums.BookingStatus;
import com.przemyslawren.escapethat.repository.BookingRepository;
import com.przemyslawren.escapethat.repository.CustomerRepository;
import com.przemyslawren.escapethat.repository.EscapeRoomRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EscapeRoomBookingService {
    private final EscapeRoomRepository escapeRoomRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final BookingMapper bookingMapper;

    private List<EscapeRoom> escapeRoomExtent;
    private List<Booking> bookingExtent;
    private List<Customer> customerExtent;

    @PostConstruct
    public void loadCache() {
        escapeRoomExtent = escapeRoomRepository.findAll();
        bookingExtent = bookingRepository.findAll();
        customerExtent = customerRepository.findAll();

        for (EscapeRoom escapeRoom : escapeRoomExtent) {
            escapeRoom.setBookings(bookingExtent
                    .stream()
                    .filter(booking -> booking.getEscapeRoom().getId().equals(escapeRoom.getId()))
                    .collect(Collectors.toList()));
        }
    }

    @PreDestroy
    @Transactional
    public void saveCache() {
        escapeRoomRepository.saveAll(escapeRoomExtent);
    }

    public void addBooking(Long escapeRoomId, BookingRequestDto bookingRequestDto) {
        Customer customer = customerExtent.stream()
                .filter(c -> c.getId().equals(bookingRequestDto.customerId()))
                .findFirst()
                .orElseThrow(() -> new EscapeRoomRuntimeException(
                        "Customer not found",
                        ErrorCode.CUSTOMER_NOT_FOUND,
                        HttpStatus.BAD_REQUEST));

        EscapeRoom escapeRoom = escapeRoomExtent.stream()
                .filter(er -> er.getId().equals(escapeRoomId))
                .findFirst()
                .orElseThrow(() -> new EscapeRoomNotFoundException(bookingRequestDto.escapeRoomId()));

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setEscapeRoom(escapeRoom);
        booking.setStartTime(bookingRequestDto.startTime());
        booking.setSlotNumber(bookingRequestDto.slotNumber());
        booking.setPromoCode(bookingRequestDto.promoCode());
        booking.setStatus(BookingStatus.CONFIRMED);

        escapeRoom.getBookings().add(booking);
    }

    public List<BookingDto> getBookingsByEscapeRoomId(Long escapeRoomId) {
        EscapeRoom escapeRoom = escapeRoomExtent.stream()
                .filter(er -> er.getId().equals(escapeRoomId))
                .findFirst()
                .orElseThrow(() -> new EscapeRoomNotFoundException(escapeRoomId));

        return escapeRoom.getBookings().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }
}
