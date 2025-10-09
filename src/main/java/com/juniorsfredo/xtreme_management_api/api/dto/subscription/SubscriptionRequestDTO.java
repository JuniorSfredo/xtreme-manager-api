package com.juniorsfredo.xtreme_management_api.api.dto.subscription;

import com.juniorsfredo.xtreme_management_api.api.dto.references.MemberReferenceDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.references.PlanReferenceDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionRequestDTO {

    @Valid
    @NotNull
    private MemberReferenceDTO member;

    @Valid
    @NotNull
    private PlanReferenceDTO plan;
}
