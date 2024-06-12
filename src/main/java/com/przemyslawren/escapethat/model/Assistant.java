package com.przemyslawren.escapethat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Assistant extends Employee {
    private String tasksPerformed;

    @OneToOne
    @JoinColumn(name = "actor_id")
    private Actor actor;
}
