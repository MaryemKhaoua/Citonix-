package com.example.citronix.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "farmes")
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
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double totalArea;

    @Column(nullable = false)
    private LocalDate creationDate;

    @OneToMany(mappedBy = "ferme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Champ> fields;


    public boolean canAddField(Champ champ) {
        return fields.size() < 10 &&
                calculateTotalFieldArea() + champ.getArea() < totalArea;
    }

    private Double calculateTotalFieldArea() {
        return fields.stream()
                .mapToDouble(Champ::getArea)
                .sum();
    }
}