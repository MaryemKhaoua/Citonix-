package com.example.citronix.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @ManyToOne
    @JoinColumn(name = "champ_id")
    private Champ champ;

    @OneToMany(mappedBy = "arbre", cascade = CascadeType.ALL)
    private List<RecolteDetail> recolteDetails;


}
