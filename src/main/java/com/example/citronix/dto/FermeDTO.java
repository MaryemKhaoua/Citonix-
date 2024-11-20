package com.example.citronix.dto;

import com.example.citronix.domain.entities.Champ;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FermeDTO {
    private long id;
    private String nom;
    private String location;
    private double surface;
    private LocalDate dateCreation;
    private List<Champ> champs;
}
