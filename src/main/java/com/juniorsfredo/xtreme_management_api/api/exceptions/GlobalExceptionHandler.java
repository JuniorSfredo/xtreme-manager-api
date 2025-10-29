package com.juniorsfredo.xtreme_management_api.api.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE = "Error internal has occurred. Try again later.";

    private MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        GlobalErrorResponse errorResponse = newErrorResponse(status.value(), ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({EntityNotFoundException.class})
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

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleGenericException(Exception ex, WebRequest request) {

        System.out.println("Exception lançada: " + ex.getClass().getName());
        System.out.println("Mensagem: " + ex.getMessage());
        System.out.println("Origem:");
        for (StackTraceElement element : ex.getStackTrace()) {
            System.out.println("\t" + element.toString());
        }

        if (ex instanceof JWTVerificationException) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            String errorMessage = "Invalid or expired authentication token";
            GlobalErrorResponse errorResponse = newErrorResponse(status.value(), errorMessage);
            return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
        }

        if (ex instanceof AccessDeniedException) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            String errorMessage = "You are not authorized to execute this operation";
            GlobalErrorResponse errorResponse = newErrorResponse(status.value(), errorMessage);
            return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
        }

        if (ex instanceof AuthenticationException) {
            HttpStatus status = HttpStatus.FORBIDDEN;
            String errorMessage = "You need authenticated to access this resource";
            GlobalErrorResponse errorResponse = newErrorResponse(status.value(), errorMessage);
            return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
        }

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        GlobalErrorResponse errorResponse = newErrorResponse(status.value(), GENERIC_ERROR_MESSAGE);
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        GlobalErrorResponse errorResponse =
                newErrorResponse(httpStatus.value(),
                        "Error has occurred in request body, verify and try again!");

        return handleExceptionInternal(ex, errorResponse, headers, httpStatus, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        List<GlobalErrorResponse.FieldError> fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String messageError = messageSource.getMessage(fieldError, Locale.US);
                    return new GlobalErrorResponse.FieldError(fieldError.getField(), messageError);
                }).toList();

        String message = "Invalid request, there are business violation, verify request and try again";
        GlobalErrorResponse errorResponse = newErrorResponse(status.value(), message);
        errorResponse.setErrors(fieldErrors);

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    private GlobalErrorResponse newErrorResponse(Integer statusCode, String message) {
        return new GlobalErrorResponse(statusCode, message, Instant.now());
    }
}
