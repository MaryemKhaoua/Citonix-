package com.example.citronix.mapper;

import com.example.citronix.domain.entities.Ferme;
import org.mapstruct.Mapper;
import com.example.citronix.dto.FermeDTO;

@Mapper(componentModel = "spring", uses = {ChampMapper.class})
public interface FermeMapper {
    FermeDTO toDto(Ferme ferme);
    Ferme toEntity(FermeDTO fermeDTO);
}
