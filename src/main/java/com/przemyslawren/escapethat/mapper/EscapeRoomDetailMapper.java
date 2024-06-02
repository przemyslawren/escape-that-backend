package com.przemyslawren.escapethat.mapper;

import com.przemyslawren.escapethat.dto.EscapeRoomDetailDto;
import com.przemyslawren.escapethat.model.EscapeRoom;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EscapeRoomDetailMapper {
    private final AddressMapper addressMapper;
    private final RoomThemeMapper roomThemeMapper;
    private final PlayerRangeMapper playerRangeMapper;

    public EscapeRoomDetailDto toDto(EscapeRoom escapeRoom) {
        return new EscapeRoomDetailDto(
                escapeRoom.getId(),
                escapeRoom.getName(),
                escapeRoom.getDescription(),
                escapeRoom.getDifficultyLevel(),
                escapeRoom.isHasActor(),
                escapeRoom.getAddress() == null
                        ? null : addressMapper.toDto(escapeRoom.getAddress()),
                escapeRoom.getRoomTheme() == null
                        ? null : roomThemeMapper.toDto(escapeRoom.getRoomTheme()),
                escapeRoom.getSafetyRequirements(),
                escapeRoom.getPlayerRange() == null
                        ? null : playerRangeMapper.toDto(escapeRoom.getPlayerRange()),
                escapeRoom.getBasePrice()
        );
    }

    public EscapeRoom toEntity(EscapeRoomDetailDto escapeRoomDetailDto) {
        EscapeRoom escapeRoom = new EscapeRoom();
        escapeRoom.setId(escapeRoomDetailDto.id());
        escapeRoom.setName(escapeRoomDetailDto.name());
        escapeRoom.setDescription(escapeRoomDetailDto.description());
        escapeRoom.setDifficultyLevel(escapeRoomDetailDto.difficultyLevel());
        escapeRoom.setHasActor(escapeRoomDetailDto.hasActor());
        escapeRoom.setAddress(escapeRoomDetailDto.address() == null
                ? null : addressMapper.toEntity(escapeRoomDetailDto.address()));
        escapeRoom.setRoomTheme(escapeRoomDetailDto.roomTheme() == null
                ? null : roomThemeMapper.toEntity(escapeRoomDetailDto.roomTheme()));
        escapeRoom.setSafetyRequirements(escapeRoomDetailDto.safetyRequirements());
        escapeRoom.setPlayerRange(escapeRoomDetailDto.playerRange() == null
                ? null : playerRangeMapper.toEntity(escapeRoomDetailDto.playerRange()));
        escapeRoom.setBasePrice(escapeRoomDetailDto.basePrice());

        return escapeRoom;
    }
}
