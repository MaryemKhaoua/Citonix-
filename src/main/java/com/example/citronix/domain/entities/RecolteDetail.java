package com.example.citronix.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecolteDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recolte_id")
//    @JsonBackReference
    private Recolte recolte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arbre_id")
    private Arbre arbre;

    private Double quantity;
}
