package com.example.citronix.service;

import com.example.citronix.domain.entities.Recolte;
import com.example.citronix.domain.entities.RecolteDetail;
import com.example.citronix.domain.enums.Saison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface RecolteService {
    Recolte createRecolte(Recolte recolte);
    RecolteDetail addRecolteDetail(Long recolteId, RecolteDetail detail);
    Page<Recolte> getAllRecoltes(Pageable pageable);
    Recolte getRecolteById(Long id);
    void deleteRecolte(Long id);
    public Recolte updateRecolte(Long id, Recolte recolte);
    boolean validateRecolteSeason(LocalDate recolteDate, Saison saison);
    boolean validateArbreRecolte(Long arbreId, Saison saison, LocalDate recolteDate);
}

