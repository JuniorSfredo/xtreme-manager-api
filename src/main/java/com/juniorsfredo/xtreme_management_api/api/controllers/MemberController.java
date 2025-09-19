package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.member.MemberRequestDTO;
import com.juniorsfredo.xtreme_management_api.domain.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/{personalId}/register-member")
    public ResponseEntity<?> registerMember(@PathVariable Long personalId, @RequestBody MemberRequestDTO member) {
        memberService.registerMember(personalId, member);
        return ResponseEntity.noContent().build();
    }
}
