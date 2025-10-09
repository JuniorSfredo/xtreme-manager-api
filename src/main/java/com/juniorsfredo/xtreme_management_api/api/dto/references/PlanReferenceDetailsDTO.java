package com.juniorsfredo.xtreme_management_api.api.dto.references;

import com.juniorsfredo.xtreme_management_api.domain.models.enums.PlanTypes;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PlanReferenceDetailsDTO {

    private Long id;

    private PlanTypes planType;

    private BigDecimal value;
}
