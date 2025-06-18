package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.assembler.AssessmentAssembler;
import com.juniorsfredo.xtreme_management_api.api.dto.assessment.AssessmentDetailsDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.assessment.AssessmentsResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.AssessmentNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.Assessment;
import com.juniorsfredo.xtreme_management_api.domain.repositories.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {

    private AssessmentRepository assessmentRepository;

    private AssessmentAssembler assessmentAssembler;

    private UserService userService;

    @Autowired
    public AssessmentService(AssessmentRepository assessmentRepository,
                             AssessmentAssembler assessmentAssembler,
                             UserService userService) {
        this.assessmentRepository = assessmentRepository;
        this.assessmentAssembler = assessmentAssembler;
        this.userService = userService;
    }

    public List<AssessmentsResponseDTO> getAllAssessmentsByUserId(Long userId, Integer page) {
        Long validUserId = userService.getUserById(userId).getId();

        int pageIndex = page <= 0 ? 0 : page - 1;
        List<Assessment> assessments =
                assessmentRepository.findAllAssessmentsByUserId(validUserId, PageRequest.of(pageIndex, 10));

        return assessments.stream()
                .map(assessment -> assessmentAssembler.toResponseDTO(assessment))
                .toList();
    }

    public AssessmentDetailsDTO getAssessmentById(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new AssessmentNotFoundException("Assessment not found with id: " + assessmentId));

        return assessmentAssembler.toDetailsResponseDTO(assessment);
    }

    public List<AssessmentsResponseDTO> getLastTwoAssessments(Long userId) {
        List<Assessment> assessments = assessmentRepository
                .findLastTwoAssessmentByUserId(userId, PageRequest.of(0, 2));

        return assessments.stream()
                .map(assessment -> assessmentAssembler.toResponseDTO(assessment))
                .toList();
    }
}
