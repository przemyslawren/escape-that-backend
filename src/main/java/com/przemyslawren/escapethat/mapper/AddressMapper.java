package com.przemyslawren.escapethat.mapper;

import com.przemyslawren.escapethat.dto.AddressDto;
import com.przemyslawren.escapethat.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressDto toDto(Address address) {
        return new AddressDto(
                address.getStreet(),
                address.getCity(),
                address.getPostalCode()
        );
    }

    public Address toEntity(AddressDto addressDto) {
        Address address = new Address();
        address.setStreet(addressDto.street());
        address.setCity(addressDto.city());
        address.setPostalCode(addressDto.postalCode());
        return address;
    }
}
