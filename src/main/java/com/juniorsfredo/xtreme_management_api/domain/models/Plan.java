package com.juniorsfredo.xtreme_management_api.domain.models;

import com.juniorsfredo.xtreme_management_api.domain.models.enums.PlanTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_plan")
@Getter
@Setter
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlanTypes plan;

    @Column(nullable = false)
    private Double value;

    @OneToMany(mappedBy = "plan")
    private List<Subscription> subscriptions = new ArrayList<>();
}
