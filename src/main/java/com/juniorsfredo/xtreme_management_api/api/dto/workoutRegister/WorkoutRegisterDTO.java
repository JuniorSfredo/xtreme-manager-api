package com.juniorsfredo.xtreme_management_api.api.dto.workoutRegister;

import com.juniorsfredo.xtreme_management_api.api.dto.references.WorkoutReference;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class WorkoutRegisterDTO {
    private Long id;
    private Instant startDate;
    private Instant endDate;
    private WorkoutReference workout;
}
