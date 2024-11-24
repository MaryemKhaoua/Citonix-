package com.example.citronix.service;

import com.example.citronix.domain.entities.Vente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VenteService {
        Vente save(Vente vente);

        Vente findById(Long id);

        Page<Vente> findAll(Pageable pageable);

        void delete(Long id);
    }