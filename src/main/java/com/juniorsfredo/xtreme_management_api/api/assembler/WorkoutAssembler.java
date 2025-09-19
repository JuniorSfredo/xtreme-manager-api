package com.juniorsfredo.xtreme_management_api.api.assembler;

import com.juniorsfredo.xtreme_management_api.api.dto.workout.WorkoutDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.workout.WorkoutRequestDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.workout.WorkoutResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Workout;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkoutAssembler {

    private ModelMapper mapper;

    @Autowired
    public WorkoutAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public WorkoutResponseDTO toWorkoutResponseDTO(Workout workout) {
        return mapper.map(workout, WorkoutResponseDTO.class);
    }

    public WorkoutDetailsResponseDTO toWorkoutDetailsResponseDTO(Workout workout) {
        return mapper.map(workout, WorkoutDetailsResponseDTO.class);
    }

    public Workout toWorkout(WorkoutRequestDTO workoutRequestDTO) {
        return mapper.map(workoutRequestDTO, Workout.class);
    }
}
