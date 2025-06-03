package com.juniorsfredo.xtreme_management_api.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_member")
@Getter
@Setter
public class Member extends User {

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "member")
    private List<Workout> workouts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Assessment> assessments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Subscription> subscriptions = new ArrayList<>();
}
