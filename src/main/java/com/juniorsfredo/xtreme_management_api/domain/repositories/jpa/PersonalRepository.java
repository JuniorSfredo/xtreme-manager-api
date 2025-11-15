package com.juniorsfredo.xtreme_management_api.domain.repositories.jpa;

import com.juniorsfredo.xtreme_management_api.domain.models.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalRepository extends JpaRepository<Personal, Long> {
}
