package com.example.citronix.dto;

import com.example.citronix.domain.entities.Ferme;
import lombok.*;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChampDTO {
    private Long id;

    @NotBlank(message = "Le nom du champ ne peut pas être vide.")
    private String name;

    @Positive(message = "La superficie doit être un nombre positif.")
    private Double area;

    @NotNull(message = "La ferme ne peut pas être nulle.")
    private Ferme ferme;
}
