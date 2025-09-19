package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.assembler.MemberAssembler;
import com.juniorsfredo.xtreme_management_api.api.dto.member.MemberIdDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.member.MemberRequestDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Member;
import com.juniorsfredo.xtreme_management_api.domain.models.Personal;
import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.RoleName;
import com.juniorsfredo.xtreme_management_api.domain.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberAssembler memberAssembler;
    private MemberRepository memberRepository;

    private PersonalService personalService;

    private RoleService roleService;

    @Autowired
    public MemberService(MemberRepository memberRepository, PersonalService personalService, RoleService roleService, MemberAssembler memberAssembler) {
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

    public void registerMember(Long personalId, MemberRequestDTO memberRequest) {
        Personal personal = personalService.getPersonalById(personalId);
        Role roleMember = roleService.findRoleByName(RoleName.ROLE_MEMBER);
        LocalDateTime now = LocalDateTime.now();
        String pwd = this.generatePwd(memberRequest.getCpf(), memberRequest.getBirthDate());
        Member member = memberAssembler.toEntity(memberRequest);
        member.setPassword(pwd);
        member.setCreatedAt(now);


    }

    @Transactional
    protected Member saveMember(Member member) {
        return this.memberRepository.save(member);
    }

    private String generatePwd(String memberCpf, LocalDate birthDate) {
        String splitCpf = memberCpf.substring(memberCpf.length() - 2);
        String day = String.format("%02d", birthDate.getDayOfMonth());
        String month = String.format("%02d", birthDate.getMonthValue());
        String year = String.valueOf(birthDate.getYear());
        return splitCpf + day + month + year;
    }
}
