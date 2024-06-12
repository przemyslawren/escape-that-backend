package com.przemyslawren.escapethat.model;

import com.przemyslawren.escapethat.model.enums.DifficultyLevel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EscapeRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;
    private boolean hasActor;

    @ManyToOne
    private Address address;

    @OneToMany(
            mappedBy = "escapeRoom",
            fetch = FetchType.LAZY, cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<Review> reviews;

    @ManyToOne
    private RoomTheme roomTheme;

    @OneToMany(
            mappedBy = "escapeRoom",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Assignment> assignments = new ArrayList<>();

    @OneToMany(
            mappedBy = "escapeRoom",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    private String safetyRequirements;

    @Embedded
    private PlayerRange playerRange;
    private int basePrice;

    @Transient
    private boolean isNew = false;

    @Transient
    private boolean isUpdated = false;

    @Transient
    private boolean isDeleted = false;
}
