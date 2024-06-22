package com.przemyslawren.escapethat.service;

import com.przemyslawren.escapethat.dto.BookingDto;
import com.przemyslawren.escapethat.dto.CustomerDto;
import com.przemyslawren.escapethat.exception.ErrorCode;
import com.przemyslawren.escapethat.exception.EscapeRoomRuntimeException;
import com.przemyslawren.escapethat.mapper.BookingMapper;
import com.przemyslawren.escapethat.mapper.CustomerMapper;
import com.przemyslawren.escapethat.model.CustomUserDetails;
import com.przemyslawren.escapethat.model.Customer;
import com.przemyslawren.escapethat.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
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
        String customerEmail = "customer@customer.com"; // tymczasowe obejście

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("Authentication: {}", authentication);
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new EscapeRoomRuntimeException(
//                    "Customer not authenticated",
//                    ErrorCode.CUSTOMER_NOT_AUTHENTICATED,
//                    HttpStatus.UNAUTHORIZED);
//        }
//
//        Object principal = authentication.getPrincipal();
//        if (!(principal instanceof CustomUserDetails)) {
//            throw new EscapeRoomRuntimeException(
//                    "Invalid user details type",
//                    ErrorCode.INVALID_USER_DETAILS,
//                    HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        CustomUserDetails userDetails = (CustomUserDetails) principal;
//        String email = userDetails.getUsername();

        Customer customer = customerExtent
                .stream()
                .filter(c -> c.getEmail().equals(customerEmail)).findAny()
                .orElseThrow(() -> new EscapeRoomRuntimeException(
                        "Customer not found",
                        ErrorCode.CUSTOMER_NOT_FOUND,
                        HttpStatus.BAD_REQUEST));

        return customer.getBookings().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }
}
