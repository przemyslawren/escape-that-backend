package com.przemyslawren.escapethat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Assignment extends BaseEntity {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee; // Polymorphic association

    @ManyToOne
    @JoinColumn(name = "escape_room_id")
    private EscapeRoom escapeRoom;
}
