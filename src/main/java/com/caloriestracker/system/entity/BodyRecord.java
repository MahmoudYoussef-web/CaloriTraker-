package com.caloriestracker.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "body_records",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id","record_date"}
        ),
        indexes = {
                @Index(name = "idx_body_user", columnList = "user_id"),
                @Index(name = "idx_body_date", columnList = "record_date")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BodyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @DecimalMin("20.0")
    @DecimalMax("300.0")
    @Column(nullable = false)
    private Double weightKg;

    @DecimalMin("50.0")
    @DecimalMax("250.0")
    @Column(nullable = false)
    private Double heightCm;

    @Column(nullable = false, updatable = false)
    private LocalDateTime recordedAt;

    @Column(nullable = false, updatable = false)
    private LocalDate recordDate;

    @PrePersist
    protected void onRecord() {
        this.recordedAt = LocalDateTime.now();
        this.recordDate = recordedAt.toLocalDate();
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable=false)
    @JsonIgnore
    private User user;

    @Column(nullable=false)
    private Double bmiAtTime;
}