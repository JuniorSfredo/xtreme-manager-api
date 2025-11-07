package com.juniorsfredo.xtreme_management_api.domain.repositories;

import com.juniorsfredo.xtreme_management_api.domain.models.WorkoutRegister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface WorkoutRegisterRepository extends JpaRepository<WorkoutRegister, Long> {

    @Query(value = "SELECT w " +
            "FROM tb_workout_register w " +
            "JOIN FETCH w.workout work " +
            "JOIN FETCH work.member m " +
            "WHERE m.id = :userId " +
            "ORDER BY w.startDate DESC",
            countQuery = "SELECT count(w) " +
                    "FROM tb_workout_register w " +
                    "JOIN w.workout work " +
                    "JOIN work.member m " +
                    "WHERE m.id = :userId")
    Page<WorkoutRegister> findRegistersByUserIdOrderByDesc(Long userId, Pageable pageable);

    @Query("SELECT r FROM tb_workout_register r " +
            "WHERE r.startDate " +
            "BETWEEN :startDate AND :endDate " +
            "ORDER BY r.startDate DESC")
    List<WorkoutRegister> findWorkoutRegistersBetweenDates(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
}
