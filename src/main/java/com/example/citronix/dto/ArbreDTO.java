package com.example.citronix.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArbreDTO {

    private Long id;

    @NotNull(message = "La date de plantation ne peut pas être null.")
    @Past(message = "La date de plantation doit être dans le passé.")
    private LocalDate plantingDate;

    @NotNull(message = "Le champ ID ne peut pas être null.")
    @Positive(message = "Le champ ID doit être un nombre positif.")
    private Long champId;
}
