package com.juniorsfredo.xtreme_management_api.domain.exceptions;

public class PlanNotFoundException extends EntityNotFoundException {
    public PlanNotFoundException(String message) {
        super(message);
    }
}
