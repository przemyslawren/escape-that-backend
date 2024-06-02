package com.przemyslawren.escapethat.model;

import com.przemyslawren.escapethat.model.enums.BookingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Booking extends BaseEntity { // association with attributes
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "escape_room_id")
    private EscapeRoom escapeRoom;

    @OneToOne
    @JoinColumn(name = "book_slot_id")
    private BookSlot bookSlot;

    @OneToOne(mappedBy = "booking")
    private Payment payment;
}
