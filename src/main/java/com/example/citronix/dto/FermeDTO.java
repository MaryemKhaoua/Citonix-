package com.example.citronix.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FermeDTO {

    private Long id;

    @NotBlank(message = "Le nom de la ferme ne peut pas être vide.")
    private String name;

    @NotBlank(message = "L'emplacement de la ferme ne peut pas être vide.")
    private String location;

    @Positive(message = "La superficie totale doit être un nombre positif.")
    private Double totalArea;

    @Past(message = "La date de création de la ferme doit être dans le passé.")
    private LocalDate creationDate;

    @JsonIgnore
    private List<ChampDTO> fields;
}
