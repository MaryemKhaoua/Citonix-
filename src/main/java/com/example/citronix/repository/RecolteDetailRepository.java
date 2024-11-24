package com.example.citronix.repository;

import com.example.citronix.domain.entities.RecolteDetail;
import com.example.citronix.domain.enums.Saison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RecolteDetailRepository extends JpaRepository<RecolteDetail, Long> {
    @Query("SELECT rd FROM RecolteDetail rd " +
            "WHERE rd.arbre.id = :arbreId " +
            "AND rd.recolte.saison = :saison " +
            "AND rd.recolte.recolteDate BETWEEN :startDate AND :endDate")
    List<RecolteDetail> findByArbreAndSaisonInPeriod(
            @Param("arbreId") Long arbreId,
            @Param("saison") Saison saison,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
