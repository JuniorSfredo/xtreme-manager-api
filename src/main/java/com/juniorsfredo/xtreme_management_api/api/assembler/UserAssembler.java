package com.juniorsfredo.xtreme_management_api.api.assembler;

import com.juniorsfredo.xtreme_management_api.api.dto.user.UserDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    private final ModelMapper mapper;

    @Autowired
    public UserAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        return mapper.map(user, UserResponseDTO.class);
    }

    public UserDetailsResponseDTO toUserDetailsResponseDTO(User user) {
        return mapper.map(user, UserDetailsResponseDTO.class);
    }
}
