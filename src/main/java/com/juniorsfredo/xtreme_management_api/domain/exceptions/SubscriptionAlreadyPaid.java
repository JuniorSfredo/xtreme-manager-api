package com.juniorsfredo.xtreme_management_api.domain.exceptions;

public class SubscriptionAlreadyPaid extends BusinessException {
    public SubscriptionAlreadyPaid(String message) {
        super(message);
    }

    public SubscriptionAlreadyPaid() {
        super("Subscription Already Paid");
    }
}
