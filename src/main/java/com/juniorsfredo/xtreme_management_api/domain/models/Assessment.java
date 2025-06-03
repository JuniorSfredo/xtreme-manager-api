package com.juniorsfredo.xtreme_management_api.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.AssessmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_assessment")
@Getter
@Setter
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double weight;

    private Double bodyfatPercentage;

    private Double imc;

    private String note;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private AssessmentStatus status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "personal_id")
    private Personal personal;

    @OneToMany(mappedBy = "assessment")
    private List<SkinFold> skinFolds = new ArrayList<>();
}
