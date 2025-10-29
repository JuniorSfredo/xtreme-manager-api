package com.juniorsfredo.xtreme_management_api.api.dto.stripe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private Long subscriptionId;
    private Long amount;
}
