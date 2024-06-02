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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    private List<EscapeRoom> cache;
    private final List<EscapeRoom> newRoomsCache = new ArrayList<>();
    private final List<EscapeRoom> updatedRoomsCache = new ArrayList<>();
    private final Set<Long> deletedRoomIdsCache = new HashSet<>();

    @PostConstruct
    public void loadCache() {
        cache = escapeRoomRepository.findAll();
    }

    @PreDestroy
    @Transactional
    public void saveCache() {
        List<EscapeRoom> updatedRooms = getUpdatedRoomsCache();
        List<EscapeRoom> newRooms = getNewRoomsCache();
        Set<Long> idsToDelete = getDeletedRoomIdsCache();

        if (!idsToDelete.isEmpty()) {
            escapeRoomRepository.deleteAllById(idsToDelete); // Only delete specified ids
        }
        if (!updatedRooms.isEmpty()) {
            escapeRoomRepository.saveAll(updatedRooms);  // Only save modified entries
        }
        if (!newRooms.isEmpty()) {
            escapeRoomRepository.saveAll(newRooms);  // Only save new entries
        }
    }

    public EscapeRoomDetailDto createEscapeRoom(EscapeRoomDetailDto escapeRoomDetailDto) {
        EscapeRoom escapeRoom = escapeRoomDetailMapper.toEntity(escapeRoomDetailDto);
        newRoomsCache.add(escapeRoom);

        return escapeRoomDetailMapper.toDto(escapeRoom);
    }

    public List<EscapeRoomSimpleDto> getAllEscapeRooms() {
       return escapeRoomSimpleMapper.toDtoList(cache);
    }

    public List<EscapeRoomSimpleDto> getAllEscapeRooms(DifficultyLevel difficultyLevel) {
        List<EscapeRoom> escapeRooms = cache.stream()
                .filter(e -> e.getDifficultyLevel().equals(difficultyLevel))
                .toList();

        return escapeRoomSimpleMapper.toDtoList(escapeRooms);
    }

    public EscapeRoomDetailDto getDetailEscapeRoom(Long id) {
        EscapeRoom escapeRoom = cache.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EscapeRoomNotFoundException(id));

        return escapeRoomDetailMapper.toDto(escapeRoom);
    }

    @Transactional
    public void deleteEscapeRoom(Long id) {
        EscapeRoom escapeRoom = cache.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EscapeRoomNotFoundException(id));

        deletedRoomIdsCache.add(escapeRoom.getId());
    }

    public int getPrice(Long id) {
        EscapeRoom escapeRoom = cache.stream()
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
}
