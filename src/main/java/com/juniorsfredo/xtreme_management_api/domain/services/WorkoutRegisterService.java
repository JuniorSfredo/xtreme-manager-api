package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.assembler.WorkoutRegisterAssembler;
import com.juniorsfredo.xtreme_management_api.api.dto.streak.WeeklyStreakDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.workout.WorkoutRegisterRequestDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.workoutRegister.PaginatedWorkoutRegisterDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Workout;
import com.juniorsfredo.xtreme_management_api.domain.models.WorkoutRegister;
import com.juniorsfredo.xtreme_management_api.domain.repositories.WorkoutRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class WorkoutRegisterService {

    private WorkoutRegisterRepository workoutRegisterRepository;

    private WorkoutRegisterAssembler workoutRegisterAssembler;

    private WorkoutService workoutService;

    @Autowired
    public WorkoutRegisterService(WorkoutRegisterRepository workoutRegisterRepository,
                                  WorkoutService workoutService,
                                  WorkoutRegisterAssembler workoutRegisterAssembler) {
        this.workoutRegisterRepository = workoutRegisterRepository;
        this.workoutService = workoutService;
        this.workoutRegisterAssembler = workoutRegisterAssembler;
    }

    public WorkoutRegister registerWorkout(WorkoutRegisterRequestDTO workoutRegisterRequest) {
        Workout workout = workoutService.findWorkoutById(workoutRegisterRequest.getWorkout().getId());
        WorkoutRegister workoutRegister = workoutRegisterAssembler.toWorkoutRegister(workoutRegisterRequest);
        workoutRegister.setWorkout(workout);
        return workoutRegisterRepository.save(workoutRegister);
    }

    public PaginatedWorkoutRegisterDTO getAllWorkoutRegistersByUserId(Long userId, Integer page, Integer size) {
        if (size < 5) size = 5;
        if (size > 20) size = 20;
        Pageable pageable = PageRequest.of(page, size);
        Page<WorkoutRegister> registers = workoutRegisterRepository.findRegistersByUserIdOrderByDesc(userId, pageable);

        if (page > registers.getTotalPages() - 1 || page < 0)
            throw new RuntimeException("Page don't exist");

        return workoutRegisterAssembler.toPaginatedWorkoutRegistersDTO(registers);
    }

    public WeeklyStreakDTO getWeeklyStreak() {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastDayOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        Instant firstDayInstant = firstDayOfWeek.atStartOfDay(zoneId).toInstant();
        Instant lastDayInstant = lastDayOfWeek.atTime(LocalTime.MAX).atZone(zoneId).toInstant();

        List<WorkoutRegister> registers =
                workoutRegisterRepository.findWorkoutRegistersBetweenDates(firstDayInstant, lastDayInstant);

        if (registers == null || registers.isEmpty()) {
            return new WeeklyStreakDTO(0, 0);
        }

        Integer maxStreak = calculateMaxWeeklyStreak(registers);
        Integer currentStreak = calculateCurrentWeeklyStreak(registers);

        return workoutRegisterAssembler.toWeeklyStreakDTO(maxStreak, currentStreak);
    }

    private Integer calculateMaxWeeklyStreak(List<WorkoutRegister> registers) {
        int currentStreak = 1;
        int maxStreak = 1;
        for (int i = 0; i < registers.size() - 1; i++) {
            Instant currentInstant = registers.get(i).getStartDate();
            Instant previousInstant = registers.get(i + 1).getStartDate();

            long daysBetween = ChronoUnit.DAYS.between(previousInstant.truncatedTo(ChronoUnit.DAYS), currentInstant.truncatedTo(ChronoUnit.DAYS));

            if (daysBetween == 1) currentStreak++;
            else if (daysBetween > 1) currentStreak = 0;
            if (currentStreak > maxStreak) maxStreak = currentStreak;
        }
        return maxStreak;
    }

    private Integer calculateCurrentWeeklyStreak (List<WorkoutRegister> registers) {
        int currentStreak = 1;
        for (int i = 0; i < registers.size() - 1; i++) {
            Instant currentInstant = registers.get(i).getStartDate();
            Instant previousInstant = registers.get(i + 1).getStartDate();

            long daysBetween = ChronoUnit.DAYS.between(
                    previousInstant.truncatedTo(ChronoUnit.DAYS),
                    currentInstant.truncatedTo(ChronoUnit.DAYS)
            );

            if (daysBetween == 1) currentStreak++;
            else break;
        }
        return currentStreak;
    }
}
