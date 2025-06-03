package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.assembler.AuthAssembler;
import com.juniorsfredo.xtreme_management_api.api.assembler.RoleAssembler;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.AccessTokenDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.UserLoginDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.UserRegisterDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final AuthenticationManager authManager;

    private final JwtService jwtService;

    private final UserService userService;

    private final AuthAssembler authAssembler;

    private final RoleAssembler roleAssembler;

    @Autowired
    public AuthService(AuthenticationConfiguration config,
                       JwtService jwtService,
                       UserService userService,
                       AuthAssembler authAssembler,
                       RoleAssembler roleAssembler
    ) throws Exception {
        this.authManager = config.getAuthenticationManager();
        this.jwtService = jwtService;
        this.userService = userService;
        this.authAssembler = authAssembler;
        this.roleAssembler = roleAssembler;
    }

    public AccessTokenDTO login(UserLoginDTO userBody) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userBody.email(), userBody.password());

        Authentication authentication = authManager.authenticate(usernamePasswordAuthenticationToken);

        String token = jwtService.generateToken((User) authentication.getPrincipal());
        return new AccessTokenDTO(token);
    }

    public UserResponseDTO register(UserRegisterDTO userBody) {
        List<Role> roleReferences =
                userBody.getRoles()
                        .stream()
                        .map(roleAssembler::roleReferenceToRole)
                        .toList();

        User user = authAssembler.userRegisterToEntityDTO(userBody);
        user.setRoles(roleReferences);
        return userService.createUser(user);
    }
}
