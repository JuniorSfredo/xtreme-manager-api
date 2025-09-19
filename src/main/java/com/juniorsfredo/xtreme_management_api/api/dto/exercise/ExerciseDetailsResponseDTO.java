package com.juniorsfredo.xtreme_management_api.api.dto.exercise;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDetailsResponseDTO {
    private Long id;

    private String name;

    private Integer seriesNumber;

    private Integer minReps;

    private Integer maxReps;
}
