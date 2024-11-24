package com.example.citronix.controller;

import com.example.citronix.domain.entities.Recolte;
import com.example.citronix.domain.entities.RecolteDetail;
import com.example.citronix.dto.RecolteDTO;
import com.example.citronix.mapper.RecolteMapper;
import com.example.citronix.service.RecolteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recoltes")
public class RecolteController {

    private final RecolteService recolteService;
    private final RecolteMapper recolteMapper;

    public RecolteController(RecolteService recolteService, RecolteMapper recolteMapper) {
        this.recolteService = recolteService;
        this.recolteMapper = recolteMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<RecolteDTO> createRecolte(@RequestBody RecolteDTO recolteDTO) {
        Recolte recolte = recolteMapper.toEntity(recolteDTO);
        Recolte savedRecolte = recolteService.createRecolte(recolte);
        return ResponseEntity.ok(recolteMapper.toDto(savedRecolte));
    }

    @PostMapping("/{recolteId}/details")
    public ResponseEntity<RecolteDetail> addRecolteDetail(
            @PathVariable Long recolteId,
            @RequestBody RecolteDetail detail) {
        return ResponseEntity.ok(recolteService.addRecolteDetail(recolteId, detail));
    }

    @GetMapping
    public ResponseEntity<Page<Recolte>> getAllRecoltes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Recolte> recoltes = recolteService.getAllRecoltes(pageable);

        return ResponseEntity.ok(recoltes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recolte> getRecolteById(@PathVariable Long id) {
        return ResponseEntity.ok(recolteService.getRecolteById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecolte(@PathVariable Long id) {
        recolteService.deleteRecolte(id);
        return ResponseEntity.ok().build();
    }
}
