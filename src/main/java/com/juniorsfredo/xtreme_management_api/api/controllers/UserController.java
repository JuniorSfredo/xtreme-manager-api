package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.user.UserDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserWeeklyStreaksResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> usersResponse = userService.findAllUsers();
        return ResponseEntity.ok(usersResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponseDTO> getUserById(@PathVariable Long id) {
        UserDetailsResponseDTO userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{id}/streaks")
    public ResponseEntity<UserWeeklyStreaksResponseDTO> getUserByIdStreaks(@PathVariable Long id) {
        UserWeeklyStreaksResponseDTO response = userService.getUserByIdWithStreaks(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailsResponseDTO> getAuthenticatedUser() {
        UserDetailsResponseDTO user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(user);
    }
}
