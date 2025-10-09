package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.assembler.SubscriptionAssembler;
import com.juniorsfredo.xtreme_management_api.api.dto.subscription.SubscriptionDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.subscription.SubscriptionRequestDTO;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.EntityNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.Plan;
import com.juniorsfredo.xtreme_management_api.domain.models.Subscription;
import com.juniorsfredo.xtreme_management_api.domain.repositories.PlanRepository;
import com.juniorsfredo.xtreme_management_api.domain.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SubscriptionService {

    private final PlanRepository planRepository;

    private SubscriptionRepository subscriptionRepository;

    private SubscriptionAssembler subscriptionAssembler;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               SubscriptionAssembler subscriptionAssembler, PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionAssembler = subscriptionAssembler;
        this.planRepository = planRepository;
    }


    @Transactional
    public SubscriptionDetailsResponseDTO createSubscription(SubscriptionRequestDTO subsciptionRequest) {

        Optional<Plan> planOpt = planRepository.findById(subsciptionRequest.getPlan().getId());
        if (planOpt.isEmpty()) throw new EntityNotFoundException("Plan not found with id: " + subsciptionRequest.getPlan().getId());

        Subscription subscription = subscriptionAssembler.toSubscription(subsciptionRequest);
        subscription.setPlan(planOpt.get());
        subscription.setExpirationDate();
        Subscription newSubscription = this.subscriptionRepository.save(subscription);

        return subscriptionAssembler.toSubscriptionDetailsResponseDTO(newSubscription);
    }
}
