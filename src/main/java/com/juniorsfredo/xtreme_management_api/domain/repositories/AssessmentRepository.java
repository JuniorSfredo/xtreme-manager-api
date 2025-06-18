package com.juniorsfredo.xtreme_management_api.domain.repositories;

import com.juniorsfredo.xtreme_management_api.domain.models.Assessment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    @Query("select assessment from Assessment assessment " +
            "join fetch assessment.personal " +
            "join fetch assessment.member member " +
            "left join fetch member.roles " +
            "where assessment.member.id = ?1")
    List<Assessment> findAllAssessmentsByUserId(Long userId, PageRequest pageable);

    @Query("select a from Assessment a " +
            "join fetch a.member m " +
            "where m.id = ?1 and a.status = 'COMPLETED'" +
            "order by a.date desc")
    List<Assessment> findLastTwoAssessmentByUserId(Long userId, Pageable pageable);
}
