package com.przemyslawren.escapethat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer extends Person {

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Review> reviews;
}
