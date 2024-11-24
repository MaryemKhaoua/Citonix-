package com.example.citronix.dto;

import com.example.citronix.domain.entities.Ferme;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChampDTO {
    private Long id;
    private String name;
    private Double area;
    private Ferme ferme;
}