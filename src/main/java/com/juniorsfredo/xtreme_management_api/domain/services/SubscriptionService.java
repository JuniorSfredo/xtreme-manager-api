package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.assembler.SubscriptionAssembler;
import com.juniorsfredo.xtreme_management_api.api.dto.subscription.SubscriptionDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.subscription.SubscriptionRequestDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.BusinessException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.EntityNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.SubscriptionAlreadyPaid;
import com.juniorsfredo.xtreme_management_api.domain.models.Plan;
import com.juniorsfredo.xtreme_management_api.domain.models.Subscription;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.PaymentStatus;
import com.juniorsfredo.xtreme_management_api.domain.repositories.PlanRepository;
import com.juniorsfredo.xtreme_management_api.domain.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SubscriptionService {

    private final UserService userService;

    private PlanRepository planRepository;

    private SubscriptionRepository subscriptionRepository;

    private SubscriptionAssembler subscriptionAssembler;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               SubscriptionAssembler subscriptionAssembler,
                               PlanRepository planRepository, UserService userService)
    {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionAssembler = subscriptionAssembler;
        this.planRepository = planRepository;
        this.userService = userService;
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

    public Subscription getSubscriptionById(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found with id: " + subscriptionId));
    }

    @Transactional
    public SubscriptionDetailsResponseDTO updatePaymentSubscription(Long subscriptionId) {
        Subscription subscription = getSubscriptionById(subscriptionId);
        subscription.setPaymentStatus(PaymentStatus.PAID);
        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return subscriptionAssembler.toSubscriptionDetailsResponseDTO(updatedSubscription);
    }

    public void verifyPaidSubscription(Subscription subscription) {
        if (subscription.getPaymentStatus().equals(PaymentStatus.PAID))
            throw new SubscriptionAlreadyPaid("Subscription with id + " + subscription.getId() + " is already paid.");
    }

    public Long getSubscriptionAmountCents(Subscription subscription) {
        BigDecimal planAmount = subscription.getPlanAmount();
        return planAmount.multiply(BigDecimal.valueOf(100)).longValue();
    }

    // TO DO
    public void verifySubscriptionActive(Subscription subscription) {
        Optional<Subscription> activeSubscription = subscriptionRepository.findByExpirationDateAfterNow(LocalDateTime.now());
        if (activeSubscription.isPresent())
            throw new BusinessException("There is already an active subscription at the moment.");
    }

    public List<SubscriptionDetailsResponseDTO> getSubscriptionsByUserId(Long userId) {
        UserDetailsResponseDTO user = userService.getUserById(userId);
        return subscriptionRepository.getAllSubscriptionsByUserId(userId);
    }

    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
    }

    public void sendEmitterPaymentSubscription(SubscriptionDetailsResponseDTO subscription) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(subscription);
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
                throw new RuntimeException(e);
            }
        }
    }
}
