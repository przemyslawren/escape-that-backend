package com.przemyslawren.escapethat.mapper;

import com.przemyslawren.escapethat.dto.CustomerDto;
import com.przemyslawren.escapethat.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDto toDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getDateOfBirth()
        );
    }

    public Customer toEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.id());
        customer.setFullName(customerDto.fullName());
        customer.setEmail(customerDto.email());
        customer.setPhoneNumber(customerDto.phoneNumber());
        customer.setDateOfBirth(customerDto.dateOfBirth());
        return customer;
    }

}
