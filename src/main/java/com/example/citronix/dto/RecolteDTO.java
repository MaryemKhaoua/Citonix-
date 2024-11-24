package com.example.citronix.dto;

import com.example.citronix.domain.enums.Saison;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RecolteDTO {
    private Saison saison;
    private LocalDate recolteDate;
    private Double totalQuantity;
    private List<RecolteDetailDTO> RecolteDetails;
}