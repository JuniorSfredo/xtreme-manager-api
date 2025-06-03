package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.user.UserDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> response = userService.findAllUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponseDTO> getUserById(@PathVariable Long id) {
        UserDetailsResponseDTO userResponse = userService.findUserById(id);
        return ResponseEntity.ok(userResponse);
    }
}
