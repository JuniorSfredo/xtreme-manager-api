package com.juniorsfredo.xtreme_management_api.domain.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotAuthenticatedException extends AuthenticationException {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
