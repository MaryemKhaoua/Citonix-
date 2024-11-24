package com.example.citronix.controller;

import com.example.citronix.domain.entities.Arbre;
import com.example.citronix.domain.entities.Champ;
import com.example.citronix.dto.ArbreDTO;
import com.example.citronix.mapper.ArbreMapper;
import com.example.citronix.service.ArbreService;
import com.example.citronix.service.ChampService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arbres")
public class ArbreController {

    private final ArbreService arbreService;
    private final ArbreMapper arbreMapper;
    private final ChampService champService;

    public ArbreController(ArbreService arbreService, ArbreMapper arbreMapper, ChampService champService) {
        this.arbreService = arbreService;
        this.arbreMapper = arbreMapper;
        this.champService = champService;
    }

    @PostMapping("/save")
    public ResponseEntity<ArbreDTO> save(@RequestBody ArbreDTO arbreDTO) {
        Arbre arbre = arbreMapper.toEntity(arbreDTO);

        Long champId = arbreDTO.getChampId();
        Champ champ = champService.findById(champId);
        if (champ == null) {
            throw new RuntimeException("Champ with ID " + champId + " not found.");
        }
        arbre.setChamp(champ);

        arbreService.validateTreeSpacing(champ, List.of(arbre));
        Arbre savedArbre = arbreService.save(arbre);

        return ResponseEntity.ok(arbreMapper.toDTO(savedArbre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArbreDTO> update(@PathVariable Long id, @RequestBody ArbreDTO arbreDTO) {
        Arbre updatedArbre = arbreMapper.toEntity(arbreDTO);

        Long champId = arbreDTO.getChampId();
        if (champId != null) {
            Champ champ = champService.findById(champId);
            if (champ == null) {
                throw new RuntimeException("Champ with ID " + champId + " not found.");
            }
            updatedArbre.setChamp(champ);
        }

        Arbre savedArbre = arbreService.update(id, updatedArbre);

        return ResponseEntity.ok(arbreMapper.toDTO(savedArbre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        arbreService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ArbreDTO>> getAll() {
        List<Arbre> arbres = arbreService.findAll();

        List<ArbreDTO> arbreDTOs = arbres.stream()
                .map(arbreMapper::toDTO)
                .toList();

        return ResponseEntity.ok(arbreDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArbreDTO> getById(@PathVariable Long id) {
        Arbre arbre = arbreService.findById(id);

        ArbreDTO arbreDTO = arbreMapper.toDTO(arbre);

        return ResponseEntity.ok(arbreDTO);
    }

    @GetMapping("/{id}/age")
    public ResponseEntity<Integer> getAge(@PathVariable Long id) {
        int age = arbreService.calculateAge(id);
        return ResponseEntity.ok(age);
    }

    @GetMapping("/{id}/productivity")
    public ResponseEntity<Double> getProductivity(@PathVariable Long id) {
        double productivity = arbreService.calculateProductivity(id);
        return ResponseEntity.ok(productivity);
    }
}
