package com.juniorsfredo.xtreme_management_api.domain.repositories;

import com.juniorsfredo.xtreme_management_api.domain.models.Assessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    @Query(value = "select assessment from Assessment assessment " +
            "join fetch assessment.personal " +
            "join fetch assessment.member member " +
            "left join fetch member.roles " +
            "where assessment.member.id = :userId and assessment.status = 'COMPLETED'",

            countQuery = "select count(assessment) from Assessment assessment " +
                    "where assessment.member.id = :userId and assessment.status = 'COMPLETED'")
    Page<Assessment> findAllCompletedAssessmentsByUserId(@Param("userId") Long userId, Pageable pageable);


    @Query("select a from Assessment a " +
            "join fetch a.member m " +
            "where m.id = ?1 and a.status = 'COMPLETED'" +
            "order by a.date desc")
    List<Assessment> findLastThreeAssessmentByUserId(Long userId, Pageable pageable);
}
