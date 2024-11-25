package com.example.citronix.domain.entities;

import com.example.citronix.domain.enums.Saison;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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