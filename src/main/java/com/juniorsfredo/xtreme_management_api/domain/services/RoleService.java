package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.domain.exceptions.EntityNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.InvalidDataException;
import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.RoleName;
import com.juniorsfredo.xtreme_management_api.domain.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<Role> verifyRolesToCreateUser(List<Role> roles) {
        List<Long> rolesIds = roles.stream()
                .map(Role::getId)
                .toList();

        List<Role> foundRoles = findRolesByIds(rolesIds);

        Set<Long> foundIds = foundRoles.stream()
                .map(Role::getId)
                .collect(Collectors.toSet());

        if (!foundIds.containsAll(rolesIds))
            throw new InvalidDataException("Invalid roles. Verify body parameter and try again!");

        return foundRoles;
    }
}
