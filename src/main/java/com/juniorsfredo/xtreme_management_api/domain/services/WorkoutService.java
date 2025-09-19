package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.assembler.WorkoutAssembler;
import com.juniorsfredo.xtreme_management_api.api.dto.workout.WorkoutDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.workout.WorkoutRequestDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.workout.WorkoutResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.BusinessException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.EntityNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.UnauthorizedException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.UserNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.*;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.RoleName;
import com.juniorsfredo.xtreme_management_api.domain.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    private final ExerciseService exerciseService;
    private final MemberService memberService;
    private final PersonalService personalService;

    private WorkoutRepository workoutRepository;

    private UserService userService;

    private WorkoutAssembler workoutAssembler;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository,
                          UserService userService,
                          WorkoutAssembler workoutAssembler,
                          ExerciseService exerciseService, MemberService memberService, PersonalService personalService) {
        this.workoutRepository = workoutRepository;
        this.userService = userService;
        this.workoutAssembler = workoutAssembler;
        this.exerciseService = exerciseService;
        this.memberService = memberService;
        this.personalService = personalService;
    }

    public List<WorkoutResponseDTO> getAllWorkoutsByUserId(Long userId) {
        userService.getUserById(userId);
        List<Workout> workouts = workoutRepository.findWorkoutsByMemberId(userId);
        return workouts.stream()
                .map((workout) -> workoutAssembler.toWorkoutResponseDTO(workout))
                .toList();
    }

    public WorkoutDetailsResponseDTO getWorkoutById(Long workoutId) {
        Workout workout = findWorkoutById(workoutId);
        WorkoutDetailsResponseDTO dto = workoutAssembler.toWorkoutDetailsResponseDTO(workout);
        dto.setExercises(exerciseService.getExcercisesByWorkoutId(workoutId));
        return dto;
    }


    public Workout createWorkout(Long personalId, Long memberId, WorkoutRequestDTO workoutRequest) {
        Member member = memberService.getMemberById(memberId);
        Personal personal = personalService.getPersonalById(personalId);
        User personalUser = userService.findUserById(personalId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + personalId));

        if (personalUser.getRoles().stream()
                .noneMatch(role -> role.getName().equals(RoleName.ROLE_PERSONAL))) {
            throw new UnauthorizedException("You do not have permission to add workout!");
        }

        Workout workout = workoutAssembler.toWorkout(workoutRequest);

        List<Exercise> exercises = workout.getExercises();

        if (exercises == null || exercises.isEmpty()) {
            throw new BusinessException("List of exercises cannot be empty");
        }

        List<Exercise> resolveExercises = exercises.stream()
                .map(exerciseService::createOrGetExercise)
                .toList();

        workout.setExercises(resolveExercises);
        workout.setMember(member);
        workout.setPersonal(personal);
        return workoutRepository.save(workout);
    }

    public Workout findWorkoutById(Long workoutId) {
        return workoutRepository.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException("Workout not found with id " + workoutId));
    }
}
