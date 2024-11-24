package com.example.citronix.repository;

import com.example.citronix.domain.entities.Arbre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ArbreRepository extends JpaRepository<Arbre, Long> {
    int countByChampId(Long champId);
}
