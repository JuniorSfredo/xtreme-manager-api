package com.juniorsfredo.xtreme_management_api.api.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordDTO {

    private String oldPassword;
    private String newPassword;
}
