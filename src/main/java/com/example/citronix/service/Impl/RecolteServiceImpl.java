package com.example.citronix.service.Impl;

import com.example.citronix.domain.entities.Arbre;
import com.example.citronix.domain.entities.Recolte;
import com.example.citronix.domain.entities.RecolteDetail;
import com.example.citronix.domain.enums.Saison;
import com.example.citronix.repository.ArbreRepository;
import com.example.citronix.repository.RecolteDetailRepository;
import com.example.citronix.repository.RecolteRepository;
import com.example.citronix.service.RecolteService;
import com.example.citronix.exception.SaisonLimitException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RecolteServiceImpl implements RecolteService {

    private final RecolteRepository recolteRepository;
    private final RecolteDetailRepository recolteDetailRepository;
    private final ArbreRepository arbreRepository;

    public RecolteServiceImpl(RecolteRepository recolteRepository,
                              RecolteDetailRepository recolteDetailRepository,
                              ArbreRepository arbreRepository) {
        this.recolteRepository = recolteRepository;
        this.recolteDetailRepository = recolteDetailRepository;
        this.arbreRepository = arbreRepository;
    }

    @Override
    @Transactional
    public Recolte createRecolte(Recolte recolte) {
        if (!validateRecolteSeason(recolte.getRecolteDate(), recolte.getSaison())) {
            throw new IllegalArgumentException("Invalid recolte season for date: " + recolte.getRecolteDate());
        }

        if (recolte.getRecolteDetails() != null) {
            for (RecolteDetail detail : recolte.getRecolteDetails()) {
                if (!validateArbreRecolte(detail.getArbre().getId(), recolte.getSaison(), recolte.getRecolteDate())) {
                    throw new SaisonLimitException(
                            "Arbre ID " + detail.getArbre().getId() + " has already been recolted in " +
                                    recolte.getSaison() + " season"
                    );
                }
            }
        }

        Recolte savedRecolte = recolteRepository.save(recolte);

        if (recolte.getRecolteDetails() != null) {
            double totalQuantity = 0.0;

            for (RecolteDetail detail : recolte.getRecolteDetails()) {
                detail.setRecolte(savedRecolte);
                detail.setQuantity(detail.getArbre().getAnnualProductivity());
                recolteDetailRepository.save(detail);
                totalQuantity += detail.getQuantity();
            }

            savedRecolte.setTotalQuantity(totalQuantity);
            savedRecolte = recolteRepository.save(savedRecolte);
        }

        return savedRecolte;
    }

    @Override
    @Transactional
    public RecolteDetail addRecolteDetail(Long recolteId, RecolteDetail detail) {
        Recolte recolte = recolteRepository.findById(recolteId)
                .orElseThrow(() -> new EntityNotFoundException("Recolte not found"));

        Arbre arbre = arbreRepository.findById(detail.getArbre().getId())
                .orElseThrow(() -> new EntityNotFoundException("Arbre not found"));

        Double annualProductivity = arbre.getAnnualProductivity();
        System.out.println("Arbre ID: " + arbre.getId() + ", Annual Productivity: " + annualProductivity);

        detail.setQuantity(annualProductivity);

        if (detail.getQuantity() == null) {
            System.out.println("Error: Quantity is null for Arbre ID: " + arbre.getId());
        }

        detail.setRecolte(recolte);

        RecolteDetail savedDetail = recolteDetailRepository.save(detail);

        recolte.setTotalQuantity(recolte.getTotalQuantity() + savedDetail.getQuantity());
        recolteRepository.save(recolte);

        return savedDetail;
    }



    @Override
    public Page<Recolte> getAllRecoltes(Pageable pageable) {

        return recolteRepository.findAll(pageable);
    }

    @Override
    public Recolte getRecolteById(Long id) {
        return recolteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recolte not found with id: " + id));
    }

    @Override
    public void deleteRecolte(Long id) {
        if (!recolteRepository.existsById(id)) {
            throw new EntityNotFoundException("Recolte not found with id: " + id);
        }
        recolteRepository.deleteById(id);
    }

    @Override
    public boolean validateRecolteSeason(LocalDate recolteDate, Saison saison) {
        int month = recolteDate.getMonthValue();
        switch (saison) {
            case WINTER:
                return (month == 12 || month == 1 || month == 2);
            case SPRING:
                return (month == 3 || month == 4 || month == 5);
            case SUMMER:
                return (month == 6 || month == 7 || month == 8);
            case AUTUMN:
                return (month == 9 || month == 10 || month == 11);
            default:
                return false;
        }
    }


    @Override
    public boolean validateArbreRecolte(Long arbreId, Saison saison, LocalDate recolteDate) {
        int year = recolteDate.getYear();
        LocalDate saisonStart;
        LocalDate saisonEnd;

        switch (saison) {
            case WINTER:
                if (recolteDate.getMonthValue() <= 2) {
                    saisonStart = LocalDate.of(year - 1, 12, 1);
                    saisonEnd = LocalDate.of(year, 2, 28);
                } else {
                    saisonStart = LocalDate.of(year, 12, 1);
                    saisonEnd = LocalDate.of(year + 1, 2, 28);
                }
                break;
            case SPRING:
                saisonStart = LocalDate.of(year, 3, 1);
                saisonEnd = LocalDate.of(year, 5, 31);
                break;
            case SUMMER     :
                saisonStart = LocalDate.of(year, 6, 1);
                saisonEnd = LocalDate.of(year, 8, 31);
                break;
            case AUTUMN:
                saisonStart = LocalDate.of(year, 9, 1);
                saisonEnd = LocalDate.of(year, 11, 30);
                break;
            default:
                throw new IllegalArgumentException("Invalid saison");
        }

        List<RecolteDetail> existingRecoltes = recolteDetailRepository.findByArbreAndSaisonInPeriod(
                arbreId,
                saison,
                saisonStart,
                saisonEnd
        );

        return existingRecoltes.isEmpty();
    }
}
