package com.juniorsfredo.xtreme_management_api.api.dto.workout;

import com.juniorsfredo.xtreme_management_api.api.dto.references.WorkoutReference;
import com.juniorsfredo.xtreme_management_api.domain.models.Workout;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class WorkoutRegisterRequest {
    private Long id;

    private Instant startDate;

    private Instant endDate;

    private WorkoutReference workout;
}
