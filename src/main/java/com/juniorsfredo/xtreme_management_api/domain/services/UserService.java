package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.dto.streak.WeeklyStreakDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.*;
import com.juniorsfredo.xtreme_management_api.api.assembler.UserAssembler;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.InvalidDataException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.UserAlreadyExistsException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.UserNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.RoleName;
import com.juniorsfredo.xtreme_management_api.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserAssembler userAssembler;

    private WorkoutRegisterService workoutRegisterService;

    private AuthService authService;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserAssembler userAssembler,
                       WorkoutRegisterService workoutRegisterService,
                       AuthService authService) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.workoutRegisterService = workoutRegisterService;
        this.authService = authService;
    }

    public List<UserResponseDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userAssembler::toUserResponseDTO).toList();
    }

    public UserDetailsResponseDTO getUserById(Long id) {
        User user = this.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return userAssembler.toUserDetailsResponseDTO(user);
    }

    public UserWeeklyStreaksResponseDTO getUserByIdWithStreaks(Long id) {
        UserDetailsResponseDTO user = this.getUserById(id);
        WeeklyStreakDTO streaks = workoutRegisterService.getWeeklyStreak();
        return userAssembler.toUserWeeklyStreakResponseDTO(user, streaks);
    }

    public UserDetailsResponseDTO getAuthenticatedUser() {
        User user = authService.getAuthenticatedUser();
        return userAssembler.toUserDetailsResponseDTO(user);
    }

    protected Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public void changeUserPassword(UserPasswordDTO userPassword) {

        User user = authService.getAuthenticatedUser();

        if (!user.getPassword().equals(userPassword.getOldPassword())) {
            throw new InvalidDataException("Old password is invalid");
        }

        user.setPassword(userPassword.getNewPassword());
        userRepository.save(user);
    }
}
