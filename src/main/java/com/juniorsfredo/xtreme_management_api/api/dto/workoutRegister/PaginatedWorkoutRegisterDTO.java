package com.juniorsfredo.xtreme_management_api.api.dto.workoutRegister;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaginatedWorkoutRegisterDTO {
    private Integer page;
    private Integer totalPages;
    private List<WorkoutRegisterDTO> workoutRegisters;
}
