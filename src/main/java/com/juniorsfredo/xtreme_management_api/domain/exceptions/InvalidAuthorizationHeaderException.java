package com.juniorsfredo.xtreme_management_api.domain.exceptions;

public class InvalidAuthorizationHeaderException extends AuthException {
    public InvalidAuthorizationHeaderException(String message) {
        super(message);
    }
}
