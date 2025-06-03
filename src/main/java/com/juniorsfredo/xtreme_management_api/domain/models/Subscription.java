package com.juniorsfredo.xtreme_management_api.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tb_subscription")
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
}
