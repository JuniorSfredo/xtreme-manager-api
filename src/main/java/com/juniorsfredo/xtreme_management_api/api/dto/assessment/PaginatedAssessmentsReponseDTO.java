package com.juniorsfredo.xtreme_management_api.api.dto.assessment;

import java.util.List;

public record PaginatedAssessmentsReponseDTO(
        Integer totalPages,
        Integer currentPage,
        List<AssessmentsResponseDTO> assessments) {}
