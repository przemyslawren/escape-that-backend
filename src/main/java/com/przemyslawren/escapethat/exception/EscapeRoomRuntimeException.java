package com.przemyslawren.escapethat.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EscapeRoomRuntimeException extends RuntimeException {
    private final ErrorCode code;
    private final HttpStatus status;

    public EscapeRoomRuntimeException(String message, ErrorCode code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
