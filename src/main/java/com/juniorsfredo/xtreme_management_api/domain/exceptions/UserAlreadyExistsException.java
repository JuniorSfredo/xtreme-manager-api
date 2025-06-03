package com.juniorsfredo.xtreme_management_api.domain.exceptions;

public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
