package com.caloriestracker.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "food",
        uniqueConstraints = @UniqueConstraint(columnNames = "name"),
        indexes = @Index(name = "idx_food_name", columnList = "name")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @DecimalMin("0.0")
    private Double calories;

    @DecimalMin("0.0")
    private Double protein;

    @DecimalMin("0.0")
    private Double carbs;

    @DecimalMin("0.0")
    private Double fat;

    private String imageUrl;

    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MealItem> mealItems = new ArrayList<>();

    @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}