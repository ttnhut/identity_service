package com.nhut.identity_service.exception;

import com.nhut.identity_service.payload.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<APIResponse<String>> handleAppException(AppException appException) {
        ErrorCode errorCode = appException.getErrorCode();

        APIResponse<String> apiResponse = new APIResponse<>();
        apiResponse.setCode(HttpStatus.BAD_REQUEST.value());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String,String> errors = new HashMap<>();

        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error->{
            String field = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(field,message);
        });

        return ResponseEntity.badRequest().body(new APIResponse<>(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_DTO.getMessage(), errors));
    }

}
