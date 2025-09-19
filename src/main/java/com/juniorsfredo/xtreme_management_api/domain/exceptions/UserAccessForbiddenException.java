package com.juniorsfredo.xtreme_management_api.domain.exceptions;


import org.springframework.security.core.AuthenticationException;

public class UserAccessForbiddenException extends AuthenticationException {
    public UserAccessForbiddenException(String message) {
        super(message);
    }
}
