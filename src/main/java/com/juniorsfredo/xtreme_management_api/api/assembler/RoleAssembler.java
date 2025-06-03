package com.juniorsfredo.xtreme_management_api.api.assembler;

import com.juniorsfredo.xtreme_management_api.api.dto.references.RoleReferenceDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleAssembler {

    private final ModelMapper modelMapper;

    @Autowired
    public RoleAssembler(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role roleReferenceToRole(RoleReferenceDTO roleReferenceDTO) {
        return modelMapper.map(roleReferenceDTO, Role.class);
    }
}
