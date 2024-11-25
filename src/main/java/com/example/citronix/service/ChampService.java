package com.example.citronix.service;

import com.example.citronix.domain.entities.Arbre;
import com.example.citronix.domain.entities.Champ;

import java.util.List;

public interface ChampService {
    Champ save(Champ champ);
    Champ update(Long id, Champ updatedChamp);
    Champ findById(Long id);
    List<Champ> findAll();
    List<Champ> findByFermeId(Long fermeId);
    void delete(Long id);
}

