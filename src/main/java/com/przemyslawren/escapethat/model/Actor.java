package com.przemyslawren.escapethat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Actor extends Employee {
    private String rolePlayed;

    @OneToOne
    @JoinColumn(name = "helper_id") // Actor can be an assistant, overlapping
    private Assistant assistant;
}
