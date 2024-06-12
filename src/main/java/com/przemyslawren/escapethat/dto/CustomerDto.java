package com.przemyslawren.escapethat.dto;

import java.time.LocalDate;

public record CustomerDto(
        Long id,
        String fullName,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth
) {
}
