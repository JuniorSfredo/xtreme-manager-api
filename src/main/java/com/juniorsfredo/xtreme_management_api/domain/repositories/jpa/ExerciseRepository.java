package com.juniorsfredo.xtreme_management_api.domain.repositories.jpa;

import com.juniorsfredo.xtreme_management_api.domain.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query("""
                       SELECT e
                       FROM Exercise e
                       JOIN e.workouts w
                       WHERE w.id = :workoutId
            """)
    List<Exercise> findExercisesByWorkoutId(@Param("workoutId") Long workoutId);

    Optional<Exercise> findByNameAndMaxRepsAndMinRepsAndSeriesNumber(
            String name, Integer maxReps, Integer minReps, Integer seriesNumber
    );
}
