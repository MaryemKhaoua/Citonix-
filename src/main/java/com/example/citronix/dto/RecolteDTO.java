package com.example.citronix.dto;

import com.example.citronix.domain.enums.Saison;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RecolteDTO {

    @NotNull(message = "La saison ne peut pas être nulle.")
    private Saison saison;

    @NotNull(message = "La date de récolte ne peut pas être nulle.")
    @PastOrPresent(message = "La date de récolte doit être aujourd'hui ou dans le passé.")
    private LocalDate recolteDate;

    @Positive(message = "La quantité totale doit être un nombre positif.")
    private Double totalQuantity;

    @JsonIgnore
    @Size(min = 1, message = "La liste des détails de récolte ne peut pas être vide.")
    private List<RecolteDetailDTO> RecolteDetails;
}
