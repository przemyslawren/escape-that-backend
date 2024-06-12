package com.przemyslawren.escapethat.model;

import com.przemyslawren.escapethat.model.enums.BookingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "escape_room_id", nullable = false)
    private EscapeRoom escapeRoom;

    private LocalDateTime startTime;
    private int slotNumber;
    private boolean promoCode;
}
