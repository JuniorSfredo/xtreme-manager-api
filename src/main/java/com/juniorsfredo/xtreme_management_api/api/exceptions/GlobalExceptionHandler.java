package com.juniorsfredo.xtreme_management_api.api.exceptions;

import com.juniorsfredo.xtreme_management_api.domain.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        GlobalErrorResponse errorResponse = newErrorResponse(status.value(), ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({RolesNotFoundException.class})
    public ResponseEntity<?> handleRolesNotFoundException(RolesNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        GlobalErrorResponse errorResponse = newErrorResponse(status.value(), ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({InvalidDataException.class})
    public ResponseEntity<?> handleInvalidDataException(InvalidDataException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        GlobalErrorResponse errorResponse = newErrorResponse(status.value(), ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({InvalidCredentialsException.class})
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        GlobalErrorResponse errorResponse = newErrorResponse(status.value(), ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    private GlobalErrorResponse newErrorResponse(Integer statusCode, String message) {
        return new GlobalErrorResponse(statusCode, message, Instant.now());
    }
}
