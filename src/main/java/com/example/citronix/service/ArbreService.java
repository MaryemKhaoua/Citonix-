package com.example.citronix.service;

import com.example.citronix.domain.entities.Arbre;
import com.example.citronix.domain.entities.Champ;

import java.util.List;

public interface ArbreService {
   Arbre save(Arbre arbre);
    void validateTreeSpacing(Champ champ, List<Arbre> arbres);
    List<Arbre> findAll();
    Arbre findById(Long id);
    Arbre update(Long id, Arbre updatedArbre);
    void delete(Long id);
 double calculateProductivity(Long arbreId);
 int calculateAge(Long arbreId);
}
