package com.juniorsfredo.xtreme_management_api.domain.repositories;

import com.juniorsfredo.xtreme_management_api.api.dto.subscription.SubscriptionDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select sub from Subscription sub where sub.expirationDate >= :now")
    Optional<Subscription> findByExpirationDateAfterNow(@Param("now") LocalDateTime now);

    @Query("""
    SELECT new com.juniorsfredo.xtreme_management_api.api.dto.subscription.SubscriptionDetailsResponseDTO(
        s.id,
        s.createdAt,
        s.expirationDate,
        s.paymentStatus,
        new com.juniorsfredo.xtreme_management_api.api.dto.references.MemberReferenceDTO(
            m.id,
            m.name
        ),
        new com.juniorsfredo.xtreme_management_api.api.dto.references.PlanReferenceDetailsDTO(
            p.id,
            p.plan,
            p.value
        )
    )
    FROM Subscription s
    JOIN s.member m
    JOIN s.plan p
    WHERE m.id = :userId
    """)
    List<SubscriptionDetailsResponseDTO> getAllSubscriptionsByUserId(Long userId);
}
