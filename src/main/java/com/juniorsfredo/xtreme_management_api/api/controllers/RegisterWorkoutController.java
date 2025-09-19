package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.workout.WorkoutRegisterRequestDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.workoutRegister.PaginatedWorkoutRegisterDTO;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.UserAccessForbiddenException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.UserNotAuthenticatedException;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import com.juniorsfredo.xtreme_management_api.domain.models.WorkoutRegister;
import com.juniorsfredo.xtreme_management_api.domain.services.WorkoutRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register-workout")
public class RegisterWorkoutController {

    private WorkoutRegisterService workoutRegisterService;

    public RegisterWorkoutController(WorkoutRegisterService workoutRegisterService) {
        this.workoutRegisterService = workoutRegisterService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<WorkoutRegister> registerWorkout(@RequestBody WorkoutRegisterRequestDTO workout,
                                                           @PathVariable String userId) {
        WorkoutRegister register = workoutRegisterService.registerWorkout(workout);
        return ResponseEntity.status(HttpStatus.CREATED).body(register);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PaginatedWorkoutRegisterDTO> getAllWorkoutRegistersByUserId(@PathVariable Long userId,
                                                                                      @RequestParam(defaultValue = "0") Integer page,
                                                                                      @RequestParam(defaultValue = "10") Integer size,
                                                                                      Authentication authentication
    ) {
        if (authentication == null) throw new UserNotAuthenticatedException("User not authenticated!");

        User user = (User) authentication.getPrincipal();
        Long loggedInUserId = user.getId();
        if (!loggedInUserId.equals(userId)) throw new UserAccessForbiddenException("Access denied to resource!");

        PaginatedWorkoutRegisterDTO registers = workoutRegisterService.getAllWorkoutRegistersByUserId(userId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(registers);
    }
}
