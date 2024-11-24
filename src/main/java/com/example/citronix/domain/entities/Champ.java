package com.example.citronix.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Champ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double area;
    @ManyToOne
    @JoinColumn(name = "ferme_id", nullable = false)
    private Ferme ferme;
    @OneToMany(mappedBy = "champ", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Arbre> arbres;

    public boolean isValidArea() {
        return area >= 0.1 &&
                area <= (ferme.getTotalArea() * 0.5);
    }

    public boolean canAddTree(Arbre arbre) {
        long treeCount = arbres.size() + 1;
        return treeCount <= (area * 100);
    }
}
