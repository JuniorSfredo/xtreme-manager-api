package com.juniorsfredo.xtreme_management_api.domain.repositories;

import com.juniorsfredo.xtreme_management_api.domain.models.WorkoutRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRegisterRepository extends JpaRepository<WorkoutRegister, Long> {
}
