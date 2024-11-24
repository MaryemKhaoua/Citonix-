package com.example.citronix.mapper;


import com.example.citronix.domain.entities.Champ;
import com.example.citronix.dto.ChampDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ArbreMapper.class})
public interface ChampMapper {
    ChampDTO ToDTO(Champ champ);
    Champ FromDTO(ChampDTO champDTO);
}
