package com.juniorsfredo.xtreme_management_api.api.dto.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.juniorsfredo.xtreme_management_api.core.validators.IsCpf;

import com.juniorsfredo.xtreme_management_api.core.validators.IsEmail;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberRequestDTO {

    @IsEmail
    private String email;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @IsCpf(message = "Invalid CPF, use the pattern: xxx.xxx.xxx-xx or xxxxxxxxxxx")
    @NotBlank(message = "CPF cannot be empty")
    private String cpf;

    @NotNull(message = "Height cannot be null")
    @Min(value = 0, message = "The minimum value accepted is 0")
    private Double height;

    @NotNull(message = "Weight cannot be null")
    @Min(value = 0, message = "The minimum value accepted is 0")
    private Double weight;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @NotEmpty(message = "Address cannot be empty")
    private String address;

    private String profileImgUrl;
}
