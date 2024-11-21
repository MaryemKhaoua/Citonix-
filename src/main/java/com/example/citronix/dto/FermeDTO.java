package com.example.citronix.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FermeDTO {

    private Long id;
    private String name;
    private String location;
    private Double totalArea;
    private LocalDate creationDate;
    private List<ChampDTO> fields;
}
