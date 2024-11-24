package com.example.citronix.mapper;

import com.example.citronix.domain.entities.Vente;
import com.example.citronix.dto.VenteDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VenteMapper {
    VenteDTO toDto(Vente vente);
    Vente toEntity(VenteDTO venteDTO);
}
