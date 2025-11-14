package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.assembler.AssessmentAssembler;
import com.juniorsfredo.xtreme_management_api.api.dto.assessment.AssessmentDetailsDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.assessment.AssessmentsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.assessment.PaginatedAssessmentsReponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.AssessmentNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.Assessment;
import com.juniorsfredo.xtreme_management_api.domain.repositories.jpa.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public PaginatedAssessmentsReponseDTO getAllAssessmentsByUserId(Long userId, Integer page) {
        Long validUserId = userService.getUserById(userId).getId();

        int pageIndex = page <= 0 ? 0 : page - 1;
        Page<Assessment> paginatedAssessments =
                assessmentRepository.findAllCompletedAssessmentsByUserId(validUserId, PageRequest.of(pageIndex, 10));

        List<Assessment> assessments = paginatedAssessments.getContent();
        Integer totalPages = paginatedAssessments.getTotalPages();
        Integer currentPage = paginatedAssessments.getNumber() + 1;

        List<AssessmentsResponseDTO> assessmentsResponseDTO = assessments.stream()
                .map(assessment -> assessmentAssembler.toResponseDTO(assessment))
                .toList();

        return assessmentAssembler.toPaginatedAssessments(totalPages, currentPage, assessmentsResponseDTO);
    }

    public AssessmentDetailsDTO getAssessmentById(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new AssessmentNotFoundException("Assessment not found with id: " + assessmentId));

        return assessmentAssembler.toDetailsResponseDTO(assessment);
    }

    public List<AssessmentsResponseDTO> getLastestAssessments(Long userId) {
        List<Assessment> assessments = assessmentRepository
                .findLastThreeAssessmentByUserId(userId, PageRequest.of(0, 3));

        return assessments.stream()
                .map(assessment -> assessmentAssembler.toResponseDTO(assessment))
                .toList();
    }
}
