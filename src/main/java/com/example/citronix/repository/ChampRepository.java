package com.example.citronix.repository;

import com.example.citronix.domain.entities.Champ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChampRepository extends JpaRepository<Champ, Long> {
    List<Champ> findByFermeId(Long fermeId);
    long countByFermeId(Long fermeId);
}
