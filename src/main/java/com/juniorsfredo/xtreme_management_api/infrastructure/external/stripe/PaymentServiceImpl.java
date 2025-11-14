package com.juniorsfredo.xtreme_management_api.infrastructure.external.stripe;

import com.juniorsfredo.xtreme_management_api.api.dto.stripe.PaymentDTO;
import com.juniorsfredo.xtreme_management_api.domain.ports.PaymentService;
import com.juniorsfredo.xtreme_management_api.infrastructure.exceptions.ExternalServiceException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentServiceImpl implements PaymentService {

    @Value("${STRIPE_SK_TEST_KEY}")
    private String stripeSkKey;

    @Value("${STRIPE_WB_SEC_KEY}")
    private String stripeWbKey;

    @Transactional
    public PaymentDTO processStripePaymentWebhook(String payload, String sigHeader) {
        Event event;
        String webhookSecret = stripeWbKey;
        Session session = null;
        PaymentDTO stripePayment =  new PaymentDTO();

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            return null;
        }

        if (event.getType().equals("checkout.session.completed")) {
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

            if (dataObjectDeserializer.getObject().isPresent()) {
                session = (Session) dataObjectDeserializer.getObject().get();
            } else {
                session = Session.GSON.fromJson(dataObjectDeserializer.getRawJson(), Session.class);
            }

            if (session.getMetadata() != null) {
                String subscriptionId = session.getMetadata().get("subscriptionId");
                stripePayment.setSubscriptionId(Long.parseLong(subscriptionId));
                stripePayment.setAmount(session.getAmountTotal());
            }
        }

        return stripePayment;
    }

    public String getStripePaymentUrl(Long amountCents, String referenceKey, String referenceValue) {
        Stripe.apiKey = stripeSkKey;
        Session session = createStripeSession(amountCents, referenceKey, referenceValue);
        return session.getUrl();
    }

    private Session createStripeSession(Long amountCents, String referenceKey, String referenceValue) {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/success.html")
                    .setCancelUrl("http://localhost:8080/cancel.html")
                    .putMetadata(referenceKey, referenceValue)
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    // Stripe test mode only accepts USD
                                                    .setCurrency("usd")
                                                    .setUnitAmount(amountCents)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Pagamento Único")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            return Session.create(params);
        } catch (StripeException e) {
            throw new ExternalServiceException("Error has occurred while generating payment URL.");
        }
    }
}
