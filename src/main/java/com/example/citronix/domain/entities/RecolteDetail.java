package com.example.citronix.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RecolteDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recolte_id")
    private Recolte recolte;

    @ManyToOne
    @JoinColumn(name = "arbre_id")
    private Arbre arbre;

    private Double quantity;
}
