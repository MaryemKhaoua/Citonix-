package com.example.citronix.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "arbres")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Arbre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate plantingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "champ_id")
    private Champ champ;

    @OneToMany(mappedBy = "arbre", cascade = CascadeType.ALL)
    private List<RecolteDetail> recolteDetails;

    public int getAge() {
        if (plantingDate != null) {
            return Period.between(plantingDate, LocalDate.now()).getYears();
        }
        return 0;
    }

    public double getAnnualProductivity() {
        int age = getAge();
        if (age < 3) {
            return 2.5;
        } else if (age <= 10) {
            return 12;
        }
        return 0;
    }

    public boolean isProductive() {

        return getAge() <= 20;
    }

    public boolean isValidPlantingPeriod() {
        if (plantingDate != null) {
            int month = plantingDate.getMonthValue();
            return month >= 3 && month <= 5;
        }
        return false;
    }
}