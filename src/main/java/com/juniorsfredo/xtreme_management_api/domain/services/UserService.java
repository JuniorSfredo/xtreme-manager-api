package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.dto.streak.WeeklyStreakDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.*;
import com.juniorsfredo.xtreme_management_api.api.assembler.UserAssembler;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.InvalidDataException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.UserNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import com.juniorsfredo.xtreme_management_api.domain.repositories.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void changePassword(Long userId, String encodedPassword) {
        userRepository.updatePassword(userId, encodedPassword);
    }

    public User getUserByEmail(String email) {
        return this.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
