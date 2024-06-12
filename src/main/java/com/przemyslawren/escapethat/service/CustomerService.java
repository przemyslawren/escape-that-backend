package com.przemyslawren.escapethat.service;

import com.przemyslawren.escapethat.dto.BookingDto;
import com.przemyslawren.escapethat.dto.BookingRequestDto;
import com.przemyslawren.escapethat.dto.CustomerDto;
import com.przemyslawren.escapethat.mapper.CustomerMapper;
import com.przemyslawren.escapethat.model.Booking;
import com.przemyslawren.escapethat.model.Customer;
import com.przemyslawren.escapethat.model.EscapeRoom;
import com.przemyslawren.escapethat.model.enums.BookingStatus;
import com.przemyslawren.escapethat.repository.BookingRepository;
import com.przemyslawren.escapethat.repository.CustomerRepository;
import com.przemyslawren.escapethat.repository.EscapeRoomRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final EscapeRoomRepository escapeRoomRepository;
    private final BookingRepository bookingRepository;
    private final CustomerMapper customerMapper;
    //TODO Work on cache, fix services and dtos

    public List<CustomerDto> getAllClients() {
        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()) {
            return null;
        }

        return customers.stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    public CustomerDto getClientById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return null;
        }

        return customerMapper.toDto(customer);
    }

    public BookingDto addBooking(CustomerDto customer, BookingRequestDto bookingRequestDto) {
        EscapeRoom escapeRoom = escapeRoomRepository
                .findById(bookingRequestDto.escapeRoomId())
                .orElse(null);

        if (escapeRoom == null) {
            return null;
        }

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setEscapeRoom(escapeRoom);
        booking.setStartTime(bookingRequestDto.startTime());
        booking.setSlotNumber(bookingRequestDto.slotNumber());
        booking.setPromoCode(bookingRequestDto.promoCode());
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);

        return new BookingDto(
                booking.getId(),
                booking.getStatus(),
                booking.getStartTime(),
                booking.getSlotNumber(),
                booking.isPromoCode()
        );
    }
}
