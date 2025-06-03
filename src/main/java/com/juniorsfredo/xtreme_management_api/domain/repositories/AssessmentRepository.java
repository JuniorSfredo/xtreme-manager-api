package com.juniorsfredo.xtreme_management_api.domain.repositories;

import com.juniorsfredo.xtreme_management_api.domain.models.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}
