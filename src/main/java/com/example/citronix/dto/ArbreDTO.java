package com.example.citronix.dto;

import com.example.citronix.domain.entities.Champ;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArbreDTO {
    private Long id;
    private LocalDate plantingDate;
    private Long champId;
}
