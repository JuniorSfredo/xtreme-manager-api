package com.juniorsfredo.xtreme_management_api.domain.repositories;

import com.juniorsfredo.xtreme_management_api.domain.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    @Query("select t from Workout t left join fetch t.member a left join fetch t.personal p where a.id = :id")
    List<Workout> findTreinosByAlunoId(Long id);

    List<Workout> findWorkoutsByMemberId(Long memberId);
}
