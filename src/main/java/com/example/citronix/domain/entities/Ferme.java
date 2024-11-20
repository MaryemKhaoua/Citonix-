package com.example.citronix.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ferme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(nullable = false)
    private String localisation;

    @Column(nullable = false)
    private double superficieTotale;

    @Column(nullable = false)
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "ferme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Champ> champs;

    public double calculerSuperficieChamps() {
        return champs.stream()
                .mapToDouble(Champ::getSuperficie)
                .sum();
    }

    public boolean isSuperficieValide() {
        return calculerSuperficieChamps() < this.superficieTotale;
    }
}