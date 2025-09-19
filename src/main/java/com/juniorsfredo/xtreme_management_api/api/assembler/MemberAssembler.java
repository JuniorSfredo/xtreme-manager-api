package com.juniorsfredo.xtreme_management_api.api.assembler;

import com.juniorsfredo.xtreme_management_api.api.dto.member.MemberRequestDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Member;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberAssembler {

    private ModelMapper mapper;

    @Autowired
    public MemberAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Member toEntity(MemberRequestDTO member) {
        return mapper.map(member, Member.class);
    }
}
