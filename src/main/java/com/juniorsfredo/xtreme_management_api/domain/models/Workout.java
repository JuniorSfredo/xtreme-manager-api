package com.juniorsfredo.xtreme_management_api.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.Muscle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_workout")
@Getter
@Setter
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Muscle name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "personal_id")
    private Personal personal;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "tb_workout_exercise",
               joinColumns = @JoinColumn(name = "workout_id"),
               inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private List<Exercise> exercises = new ArrayList<>();
}
