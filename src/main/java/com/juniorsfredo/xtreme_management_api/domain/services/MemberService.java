package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.assembler.MemberAssembler;
import com.juniorsfredo.xtreme_management_api.api.dto.member.MemberRequestDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.references.MemberReferenceDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Member;
import com.juniorsfredo.xtreme_management_api.domain.models.Personal;
import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.RoleName;
import com.juniorsfredo.xtreme_management_api.domain.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MemberService {

    private final MemberAssembler memberAssembler;

    private MemberRepository memberRepository;

    private PersonalService personalService;

    private RoleService roleService;

    @Autowired
    public MemberService(MemberRepository memberRepository,
                         PersonalService personalService,
                         RoleService roleService,
                         MemberAssembler memberAssembler) {
        this.memberRepository = memberRepository;
        this.personalService = personalService;
        this.roleService = roleService;
        this.memberAssembler = memberAssembler;
    }

    public Member getMemberById(Long id) {
        return this.findMemberById(id).orElseThrow(
                () -> new RuntimeException("Member with id " + id + " not found")
        );
    }

    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public MemberReferenceDTO createNewMember(Long personalId, MemberRequestDTO memberRequest) {
        personalService.getPersonalById(personalId);
        Role roleMember = roleService.findRoleByName(RoleName.ROLE_MEMBER);
        Member member = memberAssembler.toEntity(memberRequest);
        member.setDefaultPassword(); // Setting default password (3 initial digits CPF + @ + member first name)
        member.setRoles(Collections.singletonList(roleMember));
        Member savedMember = saveMember(member);

        return memberAssembler.toMemberReferenceDTO(savedMember);
    }

    @Transactional
    protected Member saveMember(Member member) {
        return this.memberRepository.save(member);
    }
}
