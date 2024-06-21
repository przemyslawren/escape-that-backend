package com.przemyslawren.escapethat.service;

import com.przemyslawren.escapethat.dto.BookingDto;
import com.przemyslawren.escapethat.dto.CustomerDto;
import com.przemyslawren.escapethat.exception.ErrorCode;
import com.przemyslawren.escapethat.exception.EscapeRoomRuntimeException;
import com.przemyslawren.escapethat.mapper.BookingMapper;
import com.przemyslawren.escapethat.mapper.CustomerMapper;
import com.przemyslawren.escapethat.model.Customer;
import com.przemyslawren.escapethat.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final BookingMapper bookingMapper;

    private List<Customer> customerExtent;

    @PostConstruct
    public void loadCache() {
        customerExtent = customerRepository.findAll();
    }

    @PreDestroy
    @Transactional
    public void saveCache() {
    }

    public List<CustomerDto> getAllCustomers() {

        if (customerExtent.isEmpty()) {
            return null;
        }

        return customerExtent.stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerExtent
                .stream()
                .filter(c -> c.getId().equals(id)).findAny()
                .orElseThrow(() -> new EscapeRoomRuntimeException(
                        "Customer not found",
                        ErrorCode.CUSTOMER_NOT_FOUND,
                        HttpStatus.BAD_REQUEST));

        return customerMapper.toDto(customer);
    }

    public List<BookingDto> getCustomerBookings(Long customerId) {
        Customer customer = customerExtent
                .stream()
                .filter(c -> c.getId().equals(customerId)).findAny()
                .orElseThrow(() -> new EscapeRoomRuntimeException(
                        "Customer not found",
                        ErrorCode.CUSTOMER_NOT_FOUND,
                        HttpStatus.BAD_REQUEST));

        return customer.getBookings().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getAuthenticatedCustomerBookings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new EscapeRoomRuntimeException(
                    "Customer not authenticated",
                    ErrorCode.CUSTOMER_NOT_AUTHENTICATED,
                    HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Customer customer = customerExtent
                .stream()
                .filter(c -> c.getEmail().equals(email)).findAny()
                .orElseThrow(() -> new EscapeRoomRuntimeException(
                        "Customer not found",
                        ErrorCode.CUSTOMER_NOT_FOUND,
                        HttpStatus.BAD_REQUEST));

        return customer.getBookings().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }
}
