package com.juniorsfredo.xtreme_management_api.infrastructure.exceptions;

public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String message) {
        super(message);
    }
}
