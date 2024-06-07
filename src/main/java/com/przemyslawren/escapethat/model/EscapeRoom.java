package com.przemyslawren.escapethat.model;

import com.przemyslawren.escapethat.model.enums.DifficultyLevel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // normal association bidirectional
    @ManyToOne
    private Address address;

    @OneToMany(
            mappedBy = "escapeRoom",
            fetch = FetchType.LAZY, cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<Review> reviews;

    @ManyToOne
    private RoomTheme roomTheme;

    //association qualified
    @OneToMany(
            mappedBy = "escapeRoom",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapKey(name = "startTime")
    private Map<LocalDateTime, BookSlot> bookSlots = new HashMap<>();

    // composition
    @OneToMany(
            mappedBy = "escapeRoom",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Assignment> assignments = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "escape_room_safety_requirements",
            joinColumns = @JoinColumn(name = "escape_room_id"))
    @Column(name = "requirement")
    private List<String> safetyRequirements;

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
