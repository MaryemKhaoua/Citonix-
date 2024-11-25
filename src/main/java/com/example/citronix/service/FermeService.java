package com.example.citronix.service;

import com.example.citronix.domain.entities.Ferme;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface FermeService {
    Ferme getFermeById(Long id);
    Optional<Ferme> getFermeByName(String nom);
    List<Ferme> getAllFermes();
    Ferme saveFerme(Ferme ferme);
    void deleteFerme(Long id);
    boolean existsById(Long id);
    Ferme getFermeByLocalisation(String localisation);
}
