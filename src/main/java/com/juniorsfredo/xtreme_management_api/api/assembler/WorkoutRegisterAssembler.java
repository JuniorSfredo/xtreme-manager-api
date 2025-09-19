package com.juniorsfredo.xtreme_management_api.api.assembler;

import com.juniorsfredo.xtreme_management_api.api.dto.streak.WeeklyStreakDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.workout.WorkoutRegisterRequestDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.workoutRegister.PaginatedWorkoutRegisterDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.workoutRegister.WorkoutRegisterDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Workout;
import com.juniorsfredo.xtreme_management_api.domain.models.WorkoutRegister;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.awt.print.Pageable;
import java.util.List;

@Component
public class WorkoutRegisterAssembler {

    private ModelMapper modelMapper;

    @Autowired
    public WorkoutRegisterAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public WorkoutRegister toWorkoutRegister(WorkoutRegisterRequestDTO workoutRegisterRequest) {
        return modelMapper.map(workoutRegisterRequest, WorkoutRegister.class);
    }

    public WorkoutRegisterDTO toWorkoutRegisterDTO(WorkoutRegister register) {
        return modelMapper.map(register, WorkoutRegisterDTO.class);
    }

    public PaginatedWorkoutRegisterDTO toPaginatedWorkoutRegistersDTO(Page<WorkoutRegister> registers) {
        List<WorkoutRegisterDTO> workoutRegisters = registers.stream()
                .map(register ->
                        modelMapper.map(register, WorkoutRegisterDTO.class))
                .toList();

        return new PaginatedWorkoutRegisterDTO(registers.getNumber(), registers.getTotalPages(), workoutRegisters);
    }

    public WeeklyStreakDTO toWeeklyStreakDTO(Integer maxStreak, Integer currentStreak) {
        return new WeeklyStreakDTO(maxStreak, currentStreak);
    }
}
