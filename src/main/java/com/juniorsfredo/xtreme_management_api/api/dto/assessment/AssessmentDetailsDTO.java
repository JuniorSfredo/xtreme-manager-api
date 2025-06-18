package com.juniorsfredo.xtreme_management_api.api.dto.assessment;

import com.juniorsfredo.xtreme_management_api.api.dto.references.PersonalReferenceDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.references.SkinFoldReferenceDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.AssessmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AssessmentDetailsDTO {
    private Long id;
    private Double weight;
    private Double bodyfatPercentage;
    private Double imc;
    private String note;
    private LocalDateTime date;
    private AssessmentStatus status;
    private PersonalReferenceDTO personal;
    private List<SkinFoldReferenceDTO> skinFolds;
}
