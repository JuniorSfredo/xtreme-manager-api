package com.juniorsfredo.xtreme_management_api.domain.exceptions;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
