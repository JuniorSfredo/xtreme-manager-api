package com.juniorsfredo.xtreme_management_api.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_personal")
@Getter
@Setter
public class Personal extends User {

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @JsonIgnore
    @OneToMany(mappedBy = "personal")
    private List<Workout> workouts = new ArrayList<>();

    @OneToMany(mappedBy = "personal", fetch = FetchType.LAZY)
    private List<Assessment> assessments = new ArrayList<>();
}
