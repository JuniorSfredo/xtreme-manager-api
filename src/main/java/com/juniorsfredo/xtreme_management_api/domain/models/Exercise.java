package com.juniorsfredo.xtreme_management_api.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_exercise")
@Getter
@Setter
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "number_of_sets", nullable = false)
    private Integer numberOfSets;

    @Column(name = "min_reps", nullable = false)
    private Integer minReps;

    @Column(name = "max_reps", nullable = false)
    private Integer maxReps;

    @JsonIgnore
    @ManyToMany(mappedBy = "exercises")
    private List<Workout> workouts = new ArrayList<>();
}
