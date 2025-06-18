package com.juniorsfredo.xtreme_management_api.api.assembler;

import com.juniorsfredo.xtreme_management_api.api.dto.assessment.AssessmentDetailsDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.assessment.AssessmentsResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Assessment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssessmentAssembler {

    private ModelMapper mapper;

    @Autowired
    public AssessmentAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public AssessmentsResponseDTO toResponseDTO(Assessment assessment) {
        return mapper.map(assessment, AssessmentsResponseDTO.class);
    }

    public AssessmentDetailsDTO toDetailsResponseDTO(Assessment assessment) {
        return mapper.map(assessment, AssessmentDetailsDTO.class);
    }

}
