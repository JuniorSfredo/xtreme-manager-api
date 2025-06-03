package com.juniorsfredo.xtreme_management_api.api.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.juniorsfredo.xtreme_management_api.api.dto.references.RoleReferenceDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    @NotEmpty(message = "Field 'name' is required!")
    private String name;

    @NotEmpty(message = "Field 'cpf' is required!")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato 000.000.000-00")
    private String cpf;

    @NotEmpty(message = "Field 'height' is required!")
    @Positive
    private Double height;

    @NotEmpty(message = "Field 'peso' is required!")
    @Positive(message = "Field ")
    private Double weight;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Size(min = 2, max = 200)
    private String address;

    @NotEmpty
    @Size(min = 2, max = 200)
    private String profileImgUrl;

    private List<RoleReferenceDTO> roles;

    @NotEmpty(message = "Field 'email' is required!")
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,6}$",
             message = "Invalid email, "
    )
    private String email;

    @NotEmpty
    @Size(min = 3, max = 50, message = "A senha deve conter entre 3 e 50 caracteres")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d[^A-Za-z\\d]]+$",
             message = "A senha deve conter letras, números e símbolos"
    )
    private String password;
}
