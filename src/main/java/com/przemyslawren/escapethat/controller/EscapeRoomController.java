package com.przemyslawren.escapethat.controller;

import com.przemyslawren.escapethat.dto.EscapeRoomDetailDto;
import com.przemyslawren.escapethat.dto.EscapeRoomSimpleDto;
import com.przemyslawren.escapethat.model.EscapeRoom;
import com.przemyslawren.escapethat.model.enums.DifficultyLevel;
import com.przemyslawren.escapethat.service.EscapeRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/escape-rooms")
@RequiredArgsConstructor
public class EscapeRoomController {

    private final EscapeRoomService escapeRoomService;

    @PostMapping()
    public EscapeRoomDetailDto createEscapeRoom(@RequestBody EscapeRoomDetailDto escapeRoomDetailDto) {

        return escapeRoomService.createEscapeRoom(escapeRoomDetailDto);
    }

    @GetMapping()
    public List<EscapeRoomSimpleDto> getAllEscapeRooms() {
        return escapeRoomService.getAllEscapeRooms();
    }

    @GetMapping("/")
    public List<EscapeRoomSimpleDto> getAllEscapeRoomsByDifficulty(
            @RequestParam DifficultyLevel difficulty) {

        return escapeRoomService.getAllEscapeRooms(difficulty);
    }

    @GetMapping("/{id}")
    public EscapeRoomDetailDto getDetailEscapeRoom(@PathVariable Long id) {
        return escapeRoomService.getDetailEscapeRoom(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEscapeRoom(@PathVariable Long id) {
        escapeRoomService.deleteEscapeRoom(id);
    }

    @GetMapping("/{id}/price")
    public int getBasePrice(@PathVariable Long id) {
        return escapeRoomService.getPrice(id);
    }

    @GetMapping("/{id}/priceWithPlayers")
    public int getPriceWithPlayers(@PathVariable Long id, @RequestParam int numberOfPlayers) {
        return escapeRoomService.getPrice(id, numberOfPlayers);
    }

}
