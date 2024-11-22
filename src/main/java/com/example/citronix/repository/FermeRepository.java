package com.example.citronix.repository;

import com.example.citronix.domain.entities.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FermeRepository extends JpaRepository<Ferme, Long> {
    Optional<Ferme> findByName(String nom);
    Optional<Ferme> findBylocation(String localisation);
    boolean existsById(Long id);
    boolean existsByNameAndLocation(String name, String location);

}
