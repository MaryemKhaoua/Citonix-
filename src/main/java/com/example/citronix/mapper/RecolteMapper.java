package com.example.citronix.mapper;

import com.example.citronix.domain.entities.Arbre;
import com.example.citronix.domain.entities.Recolte;
import com.example.citronix.domain.entities.RecolteDetail;
import com.example.citronix.dto.RecolteDTO;
import com.example.citronix.dto.RecolteDetailDTO;
import com.example.citronix.repository.ArbreRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecolteMapper {
    private final ArbreRepository arbreRepository;

    public RecolteMapper(ArbreRepository arbreRepository) {
        this.arbreRepository = arbreRepository;
    }

    public Recolte toEntity(RecolteDTO dto) {
        if (dto == null) {
            return null;
        }

        Recolte recolte = new Recolte();
        recolte.setSaison(dto.getSaison());
        recolte.setRecolteDate(dto.getRecolteDate());

        double totalQuantity = 0.0;
        if (dto.getRecolteDetails() != null) {
            List<RecolteDetail> details = dto.getRecolteDetails().stream()
                    .map(detailDTO -> toRecolteDetail(detailDTO, recolte))
                    .collect(Collectors.toList());

            totalQuantity = details.stream()
                    .mapToDouble(RecolteDetail::getQuantity)
                    .sum();

            recolte.setRecolteDetails(details);
        }

        recolte.setTotalQuantity(totalQuantity);
        return recolte;
    }

    private RecolteDetail toRecolteDetail(RecolteDetailDTO dto, Recolte recolte) {
        RecolteDetail detail = new RecolteDetail();
        Arbre arbre = arbreRepository.findById(dto.getArbreId())
                .orElseThrow(() -> new IllegalArgumentException("Arbre not found with id: " + dto.getArbreId()));

        detail.setQuantity(arbre.getAnnualProductivity());
        detail.setRecolte(recolte);
        detail.setArbre(arbre);

        return detail;
    }


    public RecolteDTO toDto(Recolte entity) {
        if (entity == null) {
            return null;
        }

        RecolteDTO dto = new RecolteDTO();
        dto.setSaison(entity.getSaison());
        dto.setRecolteDate(entity.getRecolteDate());
        dto.setTotalQuantity(entity.getTotalQuantity());

        if (entity.getRecolteDetails() != null) {
            List<RecolteDetailDTO> detailDTOs = entity.getRecolteDetails().stream()
                    .map(this::toRecolteDetailDto)
                    .collect(Collectors.toList());
            dto.setRecolteDetails(detailDTOs);
        }

        return dto;
    }

    private RecolteDetailDTO toRecolteDetailDto(RecolteDetail detail) {
        RecolteDetailDTO dto = new RecolteDetailDTO();
        dto.setArbreId(detail.getArbre().getId());
        return dto;
    }
}