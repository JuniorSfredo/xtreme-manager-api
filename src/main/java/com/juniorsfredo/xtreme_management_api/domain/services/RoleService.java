package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.domain.exceptions.EntityNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.RoleName;
import com.juniorsfredo.xtreme_management_api.domain.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findRolesByIds(List<Long> rolesIds) {
        return roleRepository.findAllById(rolesIds);
    }

    public Role findRoleByName(RoleName roleName) {
        return roleRepository.findByName(roleName)
                    .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }
}
