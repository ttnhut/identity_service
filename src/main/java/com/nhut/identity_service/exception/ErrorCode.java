package com.nhut.identity_service.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    USER_EXISTED(HttpStatus.BAD_REQUEST.value(), "User existed"),
    USER_NOT_EXISTED(HttpStatus.BAD_REQUEST.value(), "User is not existed"),
    INVALID_DTO(HttpStatus.BAD_REQUEST.value(), "Invalid DTO"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(), "Unauthenticated");

    int code;
    String message;
}
