package com.przemyslawren.escapethat.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class PlayerRange {
    private int minPlayers;
    private int maxPlayers;
}
