package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.subscription.SubscriptionDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.subscription.SubscriptionRequestDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Subscription;
import com.juniorsfredo.xtreme_management_api.domain.services.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping()
    public ResponseEntity<SubscriptionDetailsResponseDTO> createSubscription(@RequestBody @Valid SubscriptionRequestDTO subscriptionRequestDTO) {
        return  ResponseEntity.ok(subscriptionService.createSubscription(subscriptionRequestDTO));
    }
}
