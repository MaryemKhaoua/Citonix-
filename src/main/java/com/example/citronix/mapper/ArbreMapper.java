package com.example.citronix.mapper;

import com.example.citronix.domain.entities.Arbre;
import com.example.citronix.dto.ArbreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArbreMapper {

    @Mapping(source = "champ.id", target = "champId")
    ArbreDTO toDTO(Arbre arbre);

    @Mapping(source = "champId", target = "champ.id")
    Arbre toEntity(ArbreDTO arbreDTO);
}
