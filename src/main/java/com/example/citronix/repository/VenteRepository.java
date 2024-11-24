package com.example.citronix.repository;

import com.example.citronix.domain.entities.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenteRepository extends JpaRepository<Vente, Long> {

}
