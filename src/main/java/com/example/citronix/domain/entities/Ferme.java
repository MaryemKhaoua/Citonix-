package com.example.citronix.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "fermes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ferme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "The name of the ferme is required and cannot be blank.")
    @Size(max = 100, message = "The name of the ferme must not exceed 100 characters.")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "The location of the ferme is required and cannot be blank.")
    @Size(max = 255, message = "The location of the ferme must not exceed 255 characters.")
    private String location;

    @Column(nullable = false)
    @NotNull(message = "The total area is required.")
    @Positive(message = "The total area must be greater than zero.")
    private Double totalArea;

    @Column(nullable = false)
    @NotNull(message = "The creation date is required.")
    @PastOrPresent(message = "The creation date cannot be in the future.")
    private LocalDate creationDate;

    @OneToMany(mappedBy = "ferme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Champ> fields;

    public boolean canAddField(Champ champ) {
        return fields.size() < 10 &&
                calculateTotalFieldArea() + champ.getArea() <= totalArea;
    }

    private Double calculateTotalFieldArea() {
        return fields.stream()
                .mapToDouble(Champ::getArea)
                .sum();
    }
}