package com.caloriestracker.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.caloriestracker.system.enums.MealType;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "meals",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id","meal_date","meal_type"}
        ),
        indexes = {
                @Index(name = "idx_meal_user", columnList = "user_id"),
                @Index(name = "idx_meal_date", columnList = "meal_date")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user","items"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name="meal_date", nullable = false)
    private LocalDate mealDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable=false)
    @JsonIgnore
    private User user;

    @OneToMany(
            mappedBy = "meal",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<MealItem> items = new ArrayList<>();

    @Column(nullable=false, updatable=false)
    private LocalDate createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDate.now();
    }

    public void addItem(MealItem item){
        items.add(item);
        item.setMeal(this);
    }
}