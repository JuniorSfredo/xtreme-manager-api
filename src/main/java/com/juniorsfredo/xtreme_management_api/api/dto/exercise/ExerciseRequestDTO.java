package com.juniorsfredo.xtreme_management_api.api.dto.exercise;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseRequestDTO {

    private Long id;

    private String name;

    private Integer maxReps;

    private Integer minReps;

    private Integer seriesNumber;
}
