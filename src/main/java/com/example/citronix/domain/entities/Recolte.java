package com.example.citronix.domain.entities;

import com.example.citronix.domain.enums.Saison;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Recolte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Saison saison;

    private LocalDate recolteDate;
    private Double totalQuantity;

    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL)
    private List<RecolteDetail> recolteDetails;

    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL)
    private List<Vente> ventes;
}