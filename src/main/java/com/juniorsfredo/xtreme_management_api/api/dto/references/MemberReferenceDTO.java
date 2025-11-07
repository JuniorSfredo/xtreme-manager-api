package com.juniorsfredo.xtreme_management_api.api.dto.references;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberReferenceDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 150)
    private String name;
}
