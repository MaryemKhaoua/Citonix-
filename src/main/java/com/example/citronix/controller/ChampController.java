package com.example.citronix.controller;

import com.example.citronix.domain.entities.Champ;
import com.example.citronix.domain.entities.Ferme;
import com.example.citronix.exception.ResourceNotFoundException;
import com.example.citronix.service.ChampService;
import com.example.citronix.service.FermeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/champs")
public class ChampController {

    @Autowired
    private ChampService champService;

    @Autowired
    private FermeService fermeService;

    @PostMapping
    public ResponseEntity<Champ> addChamp(@RequestBody Champ champ) {
        Long fermeId = champ.getFerme().getId();

        Ferme ferme = fermeService.getFermeById(fermeId);

        if (ferme == null) {
            return ResponseEntity.badRequest().body(null);
        }

        champ.setFerme(ferme);

        Champ savedChamp = champService.save(champ);
        return ResponseEntity.ok(savedChamp);
    }



@PutMapping("/{id}")
    public ResponseEntity<Champ> updateChamp(@PathVariable Long id, @RequestBody Champ updatedChamp) {
        if (updatedChamp.getArea() <= 0) {
            throw new IllegalArgumentException("Champ area must be greater than 0");
        }
        return ResponseEntity.ok(champService.update(id, updatedChamp));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Champ> getChampById(@PathVariable Long id) {
        Champ champ = champService.findById(id);
        if (champ == null) {
            throw new ResourceNotFoundException("Champ with ID " + id + " not found");
        }
        return ResponseEntity.ok(champ);
    }

    @GetMapping("/ferme/{fermeId}")
    public ResponseEntity<List<Champ>> getChampsByFerme(@PathVariable Long fermeId) {
        List<Champ> champs = champService.findByFermeId(fermeId);
        if (champs.isEmpty()) {
            throw new ResourceNotFoundException("No champs found for ferme with ID " + fermeId);
        }
        return ResponseEntity.ok(champs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChamp(@PathVariable Long id) {
        champService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
