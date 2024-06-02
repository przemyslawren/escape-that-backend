package com.przemyslawren.escapethat.mapper;

import com.przemyslawren.escapethat.dto.RoomThemeDto;
import com.przemyslawren.escapethat.model.RoomTheme;
import org.springframework.stereotype.Component;

@Component
public class RoomThemeMapper {
    public RoomThemeDto toDto(RoomTheme roomTheme) {
        return new RoomThemeDto(
                roomTheme.getThemeName(),
                roomTheme.getDescription()
        );
    }

    public RoomTheme toEntity(RoomThemeDto roomThemeDto) {
        RoomTheme roomTheme = new RoomTheme();
        roomTheme.setThemeName(roomThemeDto.themeName());
        roomTheme.setDescription(roomThemeDto.description());
        return roomTheme;
    }
}
