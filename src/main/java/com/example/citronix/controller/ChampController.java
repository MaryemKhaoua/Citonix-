package com.example.citronix.controller;


import com.example.citronix.domain.entities.Champ;
import com.example.citronix.service.ChampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/champs")
public class ChampController {

    @Autowired
    private ChampService champService;

    @PostMapping
    public ResponseEntity<Champ> addChamp(@RequestBody Champ champ) {
        return ResponseEntity.ok(champService.save(champ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Champ> updateChamp(@PathVariable Long id, @RequestBody Champ updatedChamp) {
        return ResponseEntity.ok(champService.update(id, updatedChamp));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Champ> getChampById(@PathVariable Long id) {
        return ResponseEntity.ok(champService.findById(id));
    }

    @GetMapping("/ferme/{fermeId}")
    public ResponseEntity<List<Champ>> getChampsByFerme(@PathVariable Long fermeId) {
        return ResponseEntity.ok(champService.findByFermeId(fermeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChamp(@PathVariable Long id) {
        champService.delete(id);
        return ResponseEntity.noContent().build();
    }
}