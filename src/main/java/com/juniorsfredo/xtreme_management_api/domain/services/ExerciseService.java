package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.assembler.ExerciseAssembler;
import com.juniorsfredo.xtreme_management_api.api.dto.exercise.ExerciseDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.exercise.ExerciseRequestDTO;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.ExerciseAlreadyExistsException;
import com.juniorsfredo.xtreme_management_api.domain.models.Exercise;
import com.juniorsfredo.xtreme_management_api.domain.repositories.ExerciseRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private ExerciseRepository exerciseRepository;

    private ExerciseAssembler exerciseAssembler;

    public ExerciseService(ExerciseRepository exerciseRepository,
                           ExerciseAssembler exerciseAssembler) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseAssembler = exerciseAssembler;
    }

    public List<ExerciseDetailsResponseDTO> getExcercisesByWorkoutId(Long workoutId) {
        List<Exercise> exercises = exerciseRepository.findExercisesByWorkoutId(workoutId);
        return exercises.stream()
                .map(exercise -> exerciseAssembler.toExerciseDetailsDTO(exercise))
                .toList();
    }

    public Optional<Exercise> findExerciseByAttributes(String name, Integer maxReps, Integer minReps, Integer seriesNumber) {
        return exerciseRepository
                .findByNameAndMaxRepsAndMinRepsAndSeriesNumber(name, maxReps, minReps, seriesNumber);
    }

    public Exercise createOrGetExercise(Exercise exerciseBody) {
        return findExerciseByAttributes(
                exerciseBody.getName(),
                exerciseBody.getMaxReps(),
                exerciseBody.getMinReps(),
                exerciseBody.getSeriesNumber()
        ).orElseGet(() -> exerciseRepository.save(exerciseBody));
    }
}
