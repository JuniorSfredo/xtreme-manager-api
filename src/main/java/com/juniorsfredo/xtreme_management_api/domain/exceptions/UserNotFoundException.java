package com.juniorsfredo.xtreme_management_api.domain.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
