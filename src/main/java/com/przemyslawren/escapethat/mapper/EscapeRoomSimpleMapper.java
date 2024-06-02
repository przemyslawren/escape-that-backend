package com.przemyslawren.escapethat.mapper;

import com.przemyslawren.escapethat.dto.EscapeRoomSimpleDto;
import com.przemyslawren.escapethat.model.EscapeRoom;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EscapeRoomSimpleMapper {
    private final AddressMapper addressMapper;
    private final RoomThemeMapper roomThemeMapper;
    private final PlayerRangeMapper playerRangeMapper;

    public EscapeRoomSimpleDto toDto(EscapeRoom escapeRoom) {
        return (new EscapeRoomSimpleDto(
                escapeRoom.getId(),
                escapeRoom.getName(),
                escapeRoom.getDifficultyLevel(),
                escapeRoom.isHasActor(),
                escapeRoom.getAddress() == null
                        ? null : addressMapper.toDto(escapeRoom.getAddress()),
                escapeRoom.getRoomTheme() == null
                        ? null : roomThemeMapper.toDto(escapeRoom.getRoomTheme()),
                escapeRoom.getPlayerRange() == null
                        ? null : playerRangeMapper.toDto(escapeRoom.getPlayerRange()),
                escapeRoom.getBasePrice()
        ));
    }

    public List<EscapeRoomSimpleDto> toDtoList(List<EscapeRoom> escapeRooms) {
        return escapeRooms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
