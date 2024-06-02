package com.przemyslawren.escapethat.dto;

import com.przemyslawren.escapethat.model.enums.DifficultyLevel;

public record EscapeRoomSimpleDto(
        Long id,
        String name,
        DifficultyLevel difficultyLevel,
        boolean hasActor,
        AddressDto address,
        RoomThemeDto roomTheme,
        PlayerRangeDto playerRange,
        int basePrice
) {}
