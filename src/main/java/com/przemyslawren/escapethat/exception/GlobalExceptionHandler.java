package com.przemyslawren.escapethat.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse("Internal server error",
                ErrorCode.INTERNAL_SERVER_ERROR,
                null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EscapeRoomRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleEscapeRoomRuntimeException(
            EscapeRoomRuntimeException ex) {
        log.error("EscapeRoomRuntimeException caught: {}", ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getCode(), null);

        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(EscapeRoomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEscapeRoomNotFoundException(
            EscapeRoomNotFoundException ex) {
        log.error("EscapeRoomNotFoundException caught: {}", ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getCode(), null);

        return new ResponseEntity<>(response, ex.getStatus());
    }
}
