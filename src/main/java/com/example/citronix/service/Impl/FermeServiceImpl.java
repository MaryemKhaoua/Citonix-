package com.example.citronix.service.Impl;

import com.example.citronix.domain.entities.Ferme;
import com.example.citronix.exception.FermeAlreadyExistsException;
import com.example.citronix.exception.FermeNotFoundException;
import com.example.citronix.repository.FermeRepository;
import com.example.citronix.service.FermeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component(value = "fermeImpl")
public class FermeServiceImpl implements FermeService {

    private final FermeRepository fermeRepository;

    public FermeServiceImpl(FermeRepository fermeRepository) {
        this.fermeRepository = fermeRepository;
    }

    @Override
    public Ferme saveFerme(Ferme ferme) {
        if (fermeRepository.existsByNameAndLocation(ferme.getName(), ferme.getLocation())) {
            throw new FermeAlreadyExistsException(
                    "Farm with name '" + ferme.getName() + "' and localisation '" + ferme.getLocation() + "' exist"
            );
        }

        return fermeRepository.save(ferme);
    }

    @Override
    public Ferme getFermeById(Long id) {
        return fermeRepository.findById(id)
                .orElseThrow(() -> new FermeNotFoundException("Ferme not found with id: " + id));
    }

    @Override
    public Optional<Ferme> getFermeByName(String nom) {
        return fermeRepository.findByName(nom);
    }

    @Override
    public Ferme getFermeByLocalisation(String localisation) {
        return fermeRepository.findBylocation(localisation)
                .orElseThrow(() -> new FermeNotFoundException("Ferme not found with localisation: " + localisation));
    }

    @Override
    public void deleteFerme(Long id) {
        if (fermeRepository.existsById(id)) {
            fermeRepository.deleteById(id);
        } else {
            throw new FermeNotFoundException("Ferme not found with id: " + id);
        }
    }

    @Override
    public List<Ferme> getAllFermes() {
        return fermeRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return fermeRepository.existsById(id);
    }
}
