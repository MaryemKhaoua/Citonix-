package com.example.citronix.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class RecolteDetailDTO {

    private long id;

    @NotNull(message = "L'ID de l'arbre ne peut pas être null.")
    private Long arbreId;

    @Positive(message = "La quantité doit être un nombre positif.")
    private Double quantity;
}
