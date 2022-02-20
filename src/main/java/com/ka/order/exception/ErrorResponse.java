package com.ka.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public ErrorResponse(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }

}