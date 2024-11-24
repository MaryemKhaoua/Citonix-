package com.example.citronix.service.Impl;

import com.example.citronix.domain.entities.Champ;
import com.example.citronix.domain.entities.Ferme;
import com.example.citronix.exception.*;
import com.example.citronix.repository.ChampRepository;
import com.example.citronix.repository.FermeRepository;
import com.example.citronix.service.ChampService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChampServiceImpl implements ChampService {


    private final ChampRepository champRepository;
    private final FermeRepository fermeRepository;

    public ChampServiceImpl(ChampRepository champRepository, FermeRepository fermeRepository) {
        this.champRepository = champRepository;
        this.fermeRepository = fermeRepository;
    }

    @Override
    public Champ save(Champ champ) {
        Ferme ferme = fermeRepository.findById(champ.getFerme().getId())
                .orElseThrow(() -> new EntityNotFoundException("Ferme not found with id: " + champ.getFerme().getId()));

        validateChampConstraints(champ, ferme);

        champ.setFerme(ferme);

        return champRepository.save(champ);
    }

    @Override
    public Champ update(Long id, Champ updatedChamp) {
        Champ existingChamp = champRepository.findById(id)
                .orElseThrow(() -> new ChampNotFoundException("Champ not found with id: " + id));

        Ferme ferme = existingChamp.getFerme();

        validateUpdatedChampConstraints(updatedChamp, ferme, id);

        existingChamp.setName(updatedChamp.getName());
        existingChamp.setArea(updatedChamp.getArea());

        return champRepository.save(existingChamp);
    }

    @Override
    public Champ findById(Long id) {
        return champRepository.findById(id)
                .orElseThrow(() -> new ChampNotFoundException("Champ not found with id: " + id));
    }

    @Override
    public List<Champ> findByFermeId(Long fermeId) {
        if (!fermeRepository.existsById(fermeId)) {
            throw new EntityNotFoundException("Ferme not found with id: " + fermeId);
        }
        return champRepository.findByFermeId(fermeId);
    }

    @Override
    public void delete(Long id) {
        if (!champRepository.existsById(id)) {
            throw new ChampNotFoundException("Champ not found with id: " + id);
        }
        champRepository.deleteById(id);
    }

    private void validateChampConstraints(Champ champ, Ferme ferme) {
        double area = champ.getArea();

        if (area < 0.1) {
            throw new InvalidChampAreaException("Invalid field area: " + area + " hectares. Minimum required is 0.1 hectare.");
        }

        if (area > ferme.getTotalArea() * 0.5) {
            throw new ExceededChampAreaException("Invalid field area: " + area + " hectares. Maximum allowed is 50% of the farm's total area (" + (ferme.getTotalArea() * 0.5) + " hectares).");
        }

        double totalFieldArea = champRepository.findByFermeId(ferme.getId()).stream()
                .mapToDouble(Champ::getArea)
                .sum();
        if (totalFieldArea + area >= ferme.getTotalArea()) {
            throw new ExceededTotalChampAreaException("Adding this field exceeds the total farm area of " + ferme.getTotalArea() + " hectares. Current total field area: " + totalFieldArea + " hectares.");
        }

        if (champRepository.countByFermeId(ferme.getId()) >= 10) {
            throw new ExceededChampCountException("Farm already has the maximum allowed number of fields (10).");
        }
    }

    private void validateUpdatedChampConstraints(Champ updatedChamp, Ferme ferme, Long champId) {
        double area = updatedChamp.getArea();

        if (area < 0.1) {
            throw new InvalidChampAreaException("Invalid field area: " + area + " hectares. Minimum required is 0.1 hectare.");
        }

        if (area > ferme.getTotalArea() * 0.5) {
            throw new ExceededChampAreaException("Invalid field area: " + area + " hectares. Maximum allowed is 50% of the farm's total area (" + (ferme.getTotalArea() * 0.5) + " hectares).");
        }

        double totalFieldArea = champRepository.findByFermeId(ferme.getId()).stream()
                .filter(ch -> !ch.getId().equals(champId))
                .mapToDouble(Champ::getArea)
                .sum();
        if (totalFieldArea + area >= ferme.getTotalArea()) {
            throw new ExceededTotalChampAreaException("Updating this field exceeds the total farm area of " + ferme.getTotalArea() + " hectares. Current total field area: " + totalFieldArea + " hectares.");
        }
    }
}
