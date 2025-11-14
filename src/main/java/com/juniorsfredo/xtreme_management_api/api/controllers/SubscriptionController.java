package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.stripe.PaymentDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.subscription.PaymentSubscriptionResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.subscription.SubscriptionDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.subscription.SubscriptionRequestDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Subscription;
import com.juniorsfredo.xtreme_management_api.domain.ports.PaymentService;
import com.juniorsfredo.xtreme_management_api.domain.services.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    private PaymentService paymentService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService,
                                  PaymentService paymentService) {
        this.subscriptionService = subscriptionService;
        this.paymentService = paymentService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<SubscriptionDetailsResponseDTO>> getSubscriptionsByUserId(@PathVariable Long userId) {
        List<SubscriptionDetailsResponseDTO> response = subscriptionService.getSubscriptionsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<SubscriptionDetailsResponseDTO> createSubscription(@RequestBody @Valid SubscriptionRequestDTO subscriptionRequestDTO) {
        return ResponseEntity.ok(subscriptionService.createSubscription(subscriptionRequestDTO));
    }

    @PutMapping("/{subscriptionId}")
    public ResponseEntity<PaymentSubscriptionResponseDTO> paymentSubscription(@PathVariable Long subscriptionId) {
        Subscription subscription = subscriptionService.getSubscriptionById(subscriptionId);
        subscriptionService.verifyPaidSubscription(subscription);

        Long centsSubscriptionAmount = subscriptionService.getSubscriptionAmountCents(subscription);

        String paymentUrl = paymentService.getStripePaymentUrl(
            centsSubscriptionAmount,
            "subscriptionId",
            String.valueOf(subscription.getId())
        );

        PaymentSubscriptionResponseDTO paymentUrlResponse = new PaymentSubscriptionResponseDTO(paymentUrl);
        return ResponseEntity.ok(paymentUrlResponse);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        PaymentDTO payment = paymentService.processStripePaymentWebhook(payload, sigHeader);
        subscriptionService.updatePaymentSubscription(payment.getSubscriptionId());

        return ResponseEntity.ok().build();
    }
}
