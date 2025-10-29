package com.juniorsfredo.xtreme_management_api.api.assembler;

import com.juniorsfredo.xtreme_management_api.api.dto.subscription.SubscriptionDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.subscription.SubscriptionRequestDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Subscription;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionAssembler {

    private ModelMapper modelMapper;

    @Autowired
    public SubscriptionAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Subscription toSubscription(SubscriptionRequestDTO subsciptionRequest) {
        return this.modelMapper.map(subsciptionRequest, Subscription.class);
    }

    public SubscriptionDetailsResponseDTO toSubscriptionDetailsResponseDTO(Subscription subscription) {
        return this.modelMapper.map(subscription, SubscriptionDetailsResponseDTO.class);
    }
}
