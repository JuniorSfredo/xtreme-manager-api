package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.member.MemberRequestDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.references.MemberReferenceDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.RoleName;
import com.juniorsfredo.xtreme_management_api.domain.services.AuthService;
import com.juniorsfredo.xtreme_management_api.domain.services.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personals")
public class PersonalController {

    private MemberService memberService;

    private AuthService authService;

    @Autowired
    public PersonalController(MemberService memberService,
                              AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    @PreAuthorize("hasAnyRole('PERSONAL', 'ADMIN')")
    @PostMapping("/{personalId}/new-member")
    public ResponseEntity<?> createMemberUser(@PathVariable Long personalId,
                                              @RequestBody @Valid MemberRequestDTO memberRequestDTO) {
        User authenticatedUser = authService.getAuthenticatedUser();


        if (!authenticatedUser.getId().equals(personalId)) {
            throw new AccessDeniedException("You are not authorized to access this resource.");
        }

        MemberReferenceDTO member = memberService.createNewMember(personalId, memberRequestDTO);
        return ResponseEntity.ok(member);
    }
}
