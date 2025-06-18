package com.juniorsfredo.xtreme_management_api.api.dto.assessment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AssessmentsResponseDTO {
    private Long id;
    private Double imc;
    private LocalDateTime date;
    private Double bodyfatPercentage;
}
