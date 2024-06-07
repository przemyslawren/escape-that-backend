package com.przemyslawren.escapethat.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(name = "escape_room_id")
    private EscapeRoom escapeRoom;

    @OneToOne(mappedBy = "bookSlot")
    private Booking booking;
}
