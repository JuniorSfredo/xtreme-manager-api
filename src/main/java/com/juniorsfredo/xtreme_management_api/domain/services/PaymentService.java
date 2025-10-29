package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.dto.stripe.PaymentDTO;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentService {

    PaymentDTO processStripePaymentWebhook(String payload, String sigHeader);

    String getStripePaymentUrl(Long amountCents, String referenceKey, String referenceValue);
}
