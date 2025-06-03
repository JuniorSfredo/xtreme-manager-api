package com.juniorsfredo.xtreme_management_api.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity(name = "tb_workout_register")
@Getter
@Setter
public class WorkoutRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_inicio")
    private Instant startDate;

    @Column(name = "data_fim")
    private Instant endDate;

    @OneToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;
}
