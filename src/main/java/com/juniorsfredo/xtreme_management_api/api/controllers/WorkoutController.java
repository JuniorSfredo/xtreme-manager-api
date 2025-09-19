package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.workout.WorkoutDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.workout.WorkoutRequestDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.workout.WorkoutResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Workout;
import com.juniorsfredo.xtreme_management_api.domain.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private WorkoutService workoutService;

    @Autowired
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<WorkoutResponseDTO>> getAllWorkoutsByUserId(@PathVariable Long userId) {
        List<WorkoutResponseDTO> response = workoutService.getAllWorkoutsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDetailsResponseDTO> getWorkoutById(@PathVariable Long id) {
        WorkoutDetailsResponseDTO workout = workoutService.getWorkoutById(id);
        return ResponseEntity.ok(workout);
    }

    @PostMapping("/{personalId}/{memberId}/create-workout")
    public ResponseEntity<?> createWorkout(@RequestBody WorkoutRequestDTO workout,
                                           @PathVariable Long personalId,
                                           @PathVariable Long memberId
    ) {
        Workout response = workoutService.createWorkout(personalId, memberId, workout);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
