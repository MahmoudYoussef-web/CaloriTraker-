package com.caloriestracker.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "daily_summaries",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "date"}
        ),
        indexes = @Index(
                name = "idx_daily_user_date",
                columnList = "user_id,date"
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DailySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @DecimalMin("0.0")
    @Column(nullable = false)
    private Double consumedCalories;

    @DecimalMin("0.0")
    @Column(nullable = false)
    private Double targetCalories;
}