package com.example.citronix.domain.entities;

import jakarta.persistence.*;
import lombok.*;

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
//    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Tree> trees;

    public boolean isValidArea() {
        return area >= 0.1 &&
                area <= (ferme.getTotalArea() * 0.5);
    }

//    public boolean canAddTree(Tree tree) {
//        long treeCount = trees.size() + 1;
//        return treeCount <= (area * 100);
//    }
}
