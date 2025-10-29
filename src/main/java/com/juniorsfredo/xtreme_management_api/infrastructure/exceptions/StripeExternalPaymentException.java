package com.juniorsfredo.xtreme_management_api.infrastructure.exceptions;

public class StripeExternalPaymentException extends ExternalServiceException {
    public StripeExternalPaymentException(String message) {
        super(message);
    }
}
