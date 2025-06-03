package com.juniorsfredo.xtreme_management_api.domain.models;

import com.juniorsfredo.xtreme_management_api.domain.models.enums.SkinFoldType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_skin_fold")
@Getter
@Setter
public class SkinFold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SkinFoldType fold;

    private Double mm;

    @ManyToOne
    @JoinColumn(name = "assesment_id")
    private Assessment assessment;
}
