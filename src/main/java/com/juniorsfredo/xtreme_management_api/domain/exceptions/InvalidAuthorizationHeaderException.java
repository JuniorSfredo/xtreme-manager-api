package com.juniorsfredo.xtreme_management_api.domain.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidAuthorizationHeaderException extends AuthenticationException {
    public InvalidAuthorizationHeaderException(String message) {
        super(message);
    }
}
