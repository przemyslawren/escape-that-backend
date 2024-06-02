package com.przemyslawren.escapethat.dto;

import com.przemyslawren.escapethat.model.enums.DifficultyLevel;
import java.util.List;

public record EscapeRoomDetailDto(
        Long id,
        String name,
        String description,
        DifficultyLevel difficultyLevel,
        boolean hasActor,
        AddressDto address,
        RoomThemeDto roomTheme,
        List<String> safetyRequirements,
        PlayerRangeDto playerRange,
        int basePrice
) {}