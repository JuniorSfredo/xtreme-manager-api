package com.juniorsfredo.xtreme_management_api.domain.ports;

import com.juniorsfredo.xtreme_management_api.api.dto.stripe.PaymentDTO;

public interface PaymentService {

    PaymentDTO processStripePaymentWebhook(String payload, String sigHeader);

    String getStripePaymentUrl(Long amountCents, String referenceKey, String referenceValue);
}
