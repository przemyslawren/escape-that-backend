package com.przemyslawren.escapethat.mapper;

import com.przemyslawren.escapethat.dto.PlayerRangeDto;
import com.przemyslawren.escapethat.model.PlayerRange;
import org.springframework.stereotype.Component;

@Component
public class PlayerRangeMapper {
    public PlayerRangeDto toDto(PlayerRange playerRange) {
        return new PlayerRangeDto(
                playerRange.getMinPlayers(),
                playerRange.getMaxPlayers()
        );
    }

    public PlayerRange toEntity(PlayerRangeDto playerRangeDto) {
        PlayerRange playerRange = new PlayerRange();
        playerRange.setMinPlayers(playerRangeDto.minPlayers());
        playerRange.setMaxPlayers(playerRangeDto.maxPlayers());
        return playerRange;
    }
}
