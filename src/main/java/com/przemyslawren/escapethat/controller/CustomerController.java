package com.przemyslawren.escapethat.controller;

import com.przemyslawren.escapethat.dto.BookingDto;
import com.przemyslawren.escapethat.dto.BookingRequestDto;
import com.przemyslawren.escapethat.dto.CustomerDto;
import com.przemyslawren.escapethat.model.Booking;
import com.przemyslawren.escapethat.model.Customer;
import com.przemyslawren.escapethat.service.CustomerService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id}/bookings")
    public List<BookingDto> getBookingsByCustomer(@PathVariable Long id) {
        CustomerDto customerDto = customerService.getClientById(id);
        if (customerDto != null) {
            return customerDto;
        }
        return Collections.emptyList();
    }

    @PostMapping("/{id}/bookings")
    public ResponseEntity<BookingDto> addBooking(@PathVariable Long id, @RequestBody
    BookingRequestDto bookingRequestDto) {
        CustomerDto customerDto = customerService.getClientById(id);
        if (customerDto != null) {
            Booking newBooking = customerService.addBooking(customerDto, bookingRequestDto);
            return ResponseEntity.ok(newBooking);
        }
        return ResponseEntity.notFound().build();
    }
}
