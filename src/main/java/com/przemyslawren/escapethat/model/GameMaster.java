package com.przemyslawren.escapethat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GameMaster extends Employee {
    private String experienceLevel;
}
