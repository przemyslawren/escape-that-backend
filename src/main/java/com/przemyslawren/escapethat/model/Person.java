package com.przemyslawren.escapethat.model;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Person extends BaseEntity {
    // Abstract class
    private String fullName;
    private String email;
    private String phoneNumber;
}
