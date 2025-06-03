package com.juniorsfredo.xtreme_management_api.domain.repositories;

import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
