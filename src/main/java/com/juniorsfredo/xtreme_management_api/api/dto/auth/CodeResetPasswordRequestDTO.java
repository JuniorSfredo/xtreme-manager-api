package com.juniorsfredo.xtreme_management_api.api.dto.auth;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeResetPasswordRequestDTO {

    private static final String INVALID_CODE = "Invalid Code, verify your code in email and try again";

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = INVALID_CODE)
    @Size(min = 6, max = 6, message = INVALID_CODE)
    @Pattern(regexp = "\\d{6}", message = INVALID_CODE)
    private String code;
}
