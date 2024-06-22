package com.przemyslawren.escapethat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    ESCAPE_ROOM_NOT_FOUND("ESCAPE_ROOM_NOT_FOUND"),
    CUSTOMER_NOT_FOUND("CUSTOMER_NOT_FOUND"),
    UNAUTHORIZED("UNAUTHORIZED"),
    CUSTOMER_NOT_AUTHENTICATED("CUSTOMER_NOT_AUTHENTICATED"),
    INVALID_USER_DETAILS("INVALID_USER_DETAILS");

    private final String code;
}
