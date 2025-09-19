package com.juniorsfredo.xtreme_management_api.api.dto.member;

import com.juniorsfredo.xtreme_management_api.domain.annotations.IsCpf;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberRequestDTO {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @IsCpf
    private String cpf;

    @NotNull
    @Min(0)
    private Double height;

    @NotNull
    @Min(0)
    private Double weight;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    @NotEmpty
    private String address;

    private String profileImgUrl;

    @NotEmpty
    @Email
    private String email;
}
