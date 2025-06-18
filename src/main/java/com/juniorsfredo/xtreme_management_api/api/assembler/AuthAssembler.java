package com.juniorsfredo.xtreme_management_api.api.assembler;

import com.juniorsfredo.xtreme_management_api.api.dto.auth.AuthenticatedResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.AuthenticatedUserResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.UserRegisterDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthAssembler {

    private ModelMapper mapper;

    @Autowired
    public AuthAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public User userRegisterToEntityDTO(UserRegisterDTO userRegister) {
        return mapper.map(userRegister, User.class);
    }

    public AuthenticatedUserResponseDTO userToAuthenticatedUserDTO(User user) {
        return mapper.map(user, AuthenticatedUserResponseDTO.class);
    }
}
