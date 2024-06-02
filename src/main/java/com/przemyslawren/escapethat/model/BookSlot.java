package com.przemyslawren.escapethat.model;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookSlot extends BaseEntity {

    private LocalDateTime startTime;

    @OneToOne(mappedBy = "bookSlot")
    private Booking booking;
}
