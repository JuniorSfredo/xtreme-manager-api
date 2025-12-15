package com.juniorsfredo.xtreme_management_api.api.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDTO {

    private String code;

    private String newPassword;

    private String email;
}
