package com.example.citronix.service.Impl;

import com.example.citronix.domain.entities.Arbre;
import com.example.citronix.domain.entities.Champ;
import com.example.citronix.exception.*;
import com.example.citronix.repository.ArbreRepository;
import com.example.citronix.service.ArbreService;
import com.example.citronix.service.ChampService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArbreServiceImpl implements ArbreService {
    private final ArbreRepository arbreRepository;
    private final ChampService champService;

    public ArbreServiceImpl(ArbreRepository arbreRepository, ChampService champService) {
        this.arbreRepository = arbreRepository;
        this.champService = champService;
    }

    @Override
    public Arbre save(Arbre arbre) {
        Long champId = arbre.getChamp().getId();
        Champ champ = champService.findById(champId);

        if (champ == null) {
            throw new EntityNotFoundException("Champ not found with id: " + champId);
        }

        arbre.setChamp(champ);

        if (!arbre.isValidPlantingPeriod()) {
            throw new InvalidPlantingPeriodException();
        }

        if (!arbre.isProductive()) {
            throw new NonProductiveTreeException();
        }

        return arbreRepository.save(arbre);
    }


    @Override
    public void validateTreeSpacing(Champ champ, List<Arbre> arbres) {
        double champArea = champService.findById(champ.getId()).getArea();

        int maxAllowedArbres = (int) (champArea * 100);

        int existingArbreCount = arbreRepository.countByChampId(champ.getId());

        if (existingArbreCount + arbres.size() > maxAllowedArbres) {
            throw new ExceededTreeDensityException();
        }
    }
    @Override
    public List<Arbre> findAll() {
        return arbreRepository.findAll();
    }

    @Override
    public Arbre findById(Long id) {
        return arbreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arbre not found with id: " + id));
    }
    @Override
    public Arbre update(Long id, Arbre updatedArbre) {
        Arbre existingArbre = arbreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arbre not found with id: " + id));

        existingArbre.setPlantingDate(updatedArbre.getPlantingDate());

        if (updatedArbre.getChamp() != null) {
            existingArbre.setChamp(champService.findById(updatedArbre.getChamp().getId()));
        }

        if (!existingArbre.isValidPlantingPeriod()) {
            throw new InvalidPlantingPeriodException();
        }

        if (!existingArbre.isProductive()) {
            throw new NonProductiveTreeException();
        }

        return arbreRepository.save(existingArbre);
    }

    @Override
    public void delete(Long id) {
        if (!arbreRepository.existsById(id)) {
            throw new EntityNotFoundException("Arbre not found with id: " + id);
        }

        arbreRepository.deleteById(id);
    }

    @Override
    public int calculateAge(Long arbreId) {
        Arbre arbre = findById(arbreId);
        return arbre.getAge();
    }

    @Override
    public double calculateProductivity(Long arbreId) {
        Arbre arbre = findById(arbreId);
        return arbre.getAnnualProductivity();
    }

}
