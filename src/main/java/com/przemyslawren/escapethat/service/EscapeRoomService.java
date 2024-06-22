package com.przemyslawren.escapethat.service;

import com.przemyslawren.escapethat.dto.EscapeRoomDetailDto;
import com.przemyslawren.escapethat.dto.EscapeRoomSimpleDto;
import com.przemyslawren.escapethat.exception.EscapeRoomNotFoundException;
import com.przemyslawren.escapethat.mapper.EscapeRoomDetailMapper;
import com.przemyslawren.escapethat.mapper.EscapeRoomSimpleMapper;
import com.przemyslawren.escapethat.model.EscapeRoom;
import com.przemyslawren.escapethat.model.enums.DifficultyLevel;
import com.przemyslawren.escapethat.repository.EscapeRoomRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class EscapeRoomService {

    private final EscapeRoomRepository escapeRoomRepository;
    private final EscapeRoomSimpleMapper escapeRoomSimpleMapper;
    private final EscapeRoomDetailMapper escapeRoomDetailMapper;

    private List<EscapeRoom> extent;

    @PostConstruct
    public void loadCache() {
        extent = escapeRoomRepository.findAll();
    }

    @PreDestroy
    @Transactional
    public void saveCache() {
        // Zapisz nowe pokoje
        List<EscapeRoom> newRooms = extent.stream()
                .filter(EscapeRoom::isNew)
                .toList();
        for (EscapeRoom room : newRooms) {
            room.setId(null);
        }
        escapeRoomRepository.saveAll(newRooms);

        // Zapisz zaktualizowane pokoje
        List<EscapeRoom> updatedRooms = extent.stream()
                .filter(EscapeRoom::isUpdated)
                .collect(Collectors.toList());
        escapeRoomRepository.saveAll(updatedRooms);

        // Usuń pokoje
        List<Long> deletedRoomIds = extent.stream()
                .filter(EscapeRoom::isDeleted)
                .map(EscapeRoom::getId)
                .collect(Collectors.toList());
        escapeRoomRepository.deleteAllById(deletedRoomIds);

        escapeRoomRepository.flush();
    }

    public EscapeRoomDetailDto createEscapeRoom(EscapeRoomDetailDto escapeRoomDetailDto) {
        EscapeRoom escapeRoom = escapeRoomDetailMapper.toEntity(escapeRoomDetailDto);
        escapeRoom.setNew(true);
        escapeRoom.setId(null);
        extent.add(escapeRoom);
        return escapeRoomDetailMapper.toDto(escapeRoom);
    }

    public List<EscapeRoomSimpleDto> getAllEscapeRooms() {
       return escapeRoomSimpleMapper.toDtoList(extent);
    }

    public List<EscapeRoomSimpleDto> getAllEscapeRooms(DifficultyLevel difficultyLevel) {
        List<EscapeRoom> escapeRooms = extent.stream()
                .filter(e -> e.getDifficultyLevel().equals(difficultyLevel))
                .toList();

        return escapeRoomSimpleMapper.toDtoList(escapeRooms);
    }

    public EscapeRoomDetailDto getDetailEscapeRoom(Long id) {
        EscapeRoom escapeRoom = extent.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EscapeRoomNotFoundException(id));

        return escapeRoomDetailMapper.toDto(escapeRoom);
    }

    @Transactional
    public void deleteEscapeRoom(Long id) {
        EscapeRoom escapeRoom = extent.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EscapeRoomNotFoundException(id));

        escapeRoom.setDeleted(true);
    }

    public int getPrice(Long id) {
        EscapeRoom escapeRoom = extent.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EscapeRoomNotFoundException(id));

        return escapeRoom.getBasePrice();
    }

    public int getPrice(Long id, int numberOfPlayers) {
        double rate = 1.10;
        double basePrice = getPrice(id);

        return (int) ((basePrice * rate) * numberOfPlayers);
    }

    public EscapeRoomDetailDto updateEscapeRoom(Long id, EscapeRoomDetailDto escapeRoomDetailDto) {
        EscapeRoom updatedEscapeRoom = escapeRoomDetailMapper.toEntity(escapeRoomDetailDto);
        EscapeRoom existingEscapeRoom = extent.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EscapeRoomNotFoundException(id));
        existingEscapeRoom.setUpdated(true);
        extent.remove(existingEscapeRoom);
        updatedEscapeRoom.setId(id);
        extent.add(updatedEscapeRoom);

        return escapeRoomDetailMapper.toDto(updatedEscapeRoom);
    }
}
