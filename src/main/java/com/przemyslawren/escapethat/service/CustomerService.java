package com.przemyslawren.escapethat.service;

import com.przemyslawren.escapethat.dto.CustomerDto;
import com.przemyslawren.escapethat.exception.ErrorCode;
import com.przemyslawren.escapethat.exception.EscapeRoomRuntimeException;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
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
}
