package com.caloriestracker.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

@Entity
@Table(
        name = "meal_items",
        indexes = {
                @Index(name = "idx_item_meal", columnList = "meal_id"),
                @Index(name = "idx_item_food", columnList = "food_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MealItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meal_id", nullable=false)
    @JsonIgnore
    private Meal meal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "food_id", nullable=false)
    private Food food;

    @DecimalMin("0.1")
    @Column(nullable = false)
    private Double quantity;

    // Snapshot
    @Column(nullable=false)
    private Double caloriesAtTime;

    @Column(nullable=false)
    private Double proteinAtTime;

    @Column(nullable=false)
    private Double carbsAtTime;

    @Column(nullable=false)
    private Double fatAtTime;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", unique = true)
    private Image image;

    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double confidence;
}