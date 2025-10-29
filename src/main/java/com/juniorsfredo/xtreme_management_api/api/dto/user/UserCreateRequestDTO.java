package com.juniorsfredo.xtreme_management_api.api.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.juniorsfredo.xtreme_management_api.core.validators.IsCpf;
import com.juniorsfredo.xtreme_management_api.core.validators.IsEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreateRequestDTO {

}
