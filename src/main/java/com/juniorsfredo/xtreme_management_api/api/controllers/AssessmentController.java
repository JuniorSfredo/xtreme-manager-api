package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.assessment.AssessmentDetailsDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.assessment.AssessmentsResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.services.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {

    private AssessmentService assessmentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/users/{userId}/")
    public ResponseEntity<List<AssessmentsResponseDTO>> getAllAssessmentsByUserId(@PathVariable Long userId,
                                                                                  @RequestParam(
                                                                                          name = "page",
                                                                                          defaultValue = "0")
                                                                                  Integer page) {
        List<AssessmentsResponseDTO> response = assessmentService.getAllAssessmentsByUserId(userId, page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{assessmentId}")
    public ResponseEntity<AssessmentDetailsDTO> getAssessmentById(@PathVariable Long assessmentId) {
        AssessmentDetailsDTO response = assessmentService.getAssessmentById(assessmentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/latest-two")
    public ResponseEntity<List<AssessmentsResponseDTO>> getLatestTwoAssessmentsByUserId(@PathVariable Long userId) {
        List<AssessmentsResponseDTO> response = assessmentService.getLastTwoAssessments(userId);
        return ResponseEntity.ok(response);
    }
}
