package com.juniorsfredo.xtreme_management_api.domain.repositories;

import com.juniorsfredo.xtreme_management_api.domain.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
