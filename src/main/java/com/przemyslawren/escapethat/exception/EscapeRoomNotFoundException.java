package com.przemyslawren.escapethat.exception;

import org.springframework.http.HttpStatus;

public class EscapeRoomNotFoundException extends EscapeRoomRuntimeException {

    public EscapeRoomNotFoundException(Long id) {
        super("Can't find Escape Room by ID [%d]".formatted(id),
                ErrorCode.ESCAPE_ROOM_NOT_FOUND,
                HttpStatus.NOT_FOUND);
    }
}
