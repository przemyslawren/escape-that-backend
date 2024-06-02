package com.przemyslawren.escapethat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RoomTheme extends BaseEntity {
    private String themeName;
    private String description;

    @OneToMany(mappedBy = "roomTheme")
    private List<EscapeRoom> escapeRooms;
}
