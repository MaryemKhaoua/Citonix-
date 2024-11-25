package com.example.citronix.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Double unitPrice;
    private Double quantity;
    private String client;
    private Double revenue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recolte_id")
    private Recolte recolte;
}
