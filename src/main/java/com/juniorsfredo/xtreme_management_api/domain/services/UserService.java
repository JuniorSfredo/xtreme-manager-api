package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.dto.user.UserDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.assembler.UserAssembler;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.InvalidDataException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.UserAlreadyExistsException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.UserNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import com.juniorsfredo.xtreme_management_api.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserAssembler userAssembler;

    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserAssembler userAssembler,
                       RoleService roleService) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.roleService = roleService;
    }

    public List<UserResponseDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userAssembler::toUserResponseDTO).toList();
    }

    @Transactional
    public UserResponseDTO createUser(User user) {
        if (userRepository.findByEmailOrCpf(user.getEmail(), user.getCpf()).isPresent())
            throw new UserAlreadyExistsException("User already exists");

        List<Role> roles = verifyRolesToCreateUser(user.getRoles());
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return userAssembler.toUserResponseDTO(savedUser);
    }

    public UserDetailsResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return userAssembler.toUserDetailsResponseDTO(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    private List<Role> verifyRolesToCreateUser(List<Role> roles) {
        List<Long> rolesIds = roles.stream()
                .map(Role::getId)
                .toList();

        List<Role> foundRoles = roleService.findRolesByIds(rolesIds);

        Set<Long> foundIds = foundRoles.stream()
                .map(Role::getId)
                .collect(Collectors.toSet());

        if (!foundIds.containsAll(rolesIds))
            throw new InvalidDataException("Invalid roles. Verify body parameter and try again!");

        return foundRoles;
    }
}
