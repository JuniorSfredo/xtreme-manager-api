package com.juniorsfredo.xtreme_management_api.api.dto.workout;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.juniorsfredo.xtreme_management_api.api.dto.references.WorkoutReference;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class WorkoutRegisterRequestDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    private Instant startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    private Instant endDate;

    private WorkoutReference workout;
}
