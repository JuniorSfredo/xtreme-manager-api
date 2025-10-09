package com.juniorsfredo.xtreme_management_api.api.dto.subscription;

import com.juniorsfredo.xtreme_management_api.api.dto.references.MemberReferenceDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.references.PlanReferenceDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.references.PlanReferenceDetailsDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SubscriptionDetailsResponseDTO {

    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime expirationDate;

    private PaymentStatus paymentStatus;

    private MemberReferenceDTO member;

    private PlanReferenceDetailsDTO plan;
}
