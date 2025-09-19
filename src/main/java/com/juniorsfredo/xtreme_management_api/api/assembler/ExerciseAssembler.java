package com.juniorsfredo.xtreme_management_api.api.assembler;

import com.juniorsfredo.xtreme_management_api.api.dto.exercise.ExerciseDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.exercise.ExerciseRequestDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Exercise;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExerciseAssembler {

    private ModelMapper modelMapper;

    @Autowired
    public ExerciseAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ExerciseDetailsResponseDTO toExerciseDetailsDTO(Exercise exercise) {
        return modelMapper.map(exercise, ExerciseDetailsResponseDTO.class);
    }

    public Exercise toExercise(ExerciseRequestDTO exerciseRequestDTO) {
        return modelMapper.map(exerciseRequestDTO, Exercise.class);
    }
}
