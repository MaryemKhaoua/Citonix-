package com.example.citronix.controller;

import com.example.citronix.domain.entities.Ferme;
import com.example.citronix.service.FermeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fermes")
public class FermeController {

    private final FermeService fermeService;

    @Autowired
    public FermeController(FermeService fermeService) {
        this.fermeService = fermeService;
    }

    @PostMapping
    public ResponseEntity<Ferme> createFerme(@RequestBody Ferme ferme) {
        try {
            Ferme savedFerme = fermeService.saveFerme(ferme);
            return new ResponseEntity<>(savedFerme, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Ferme>> getAllFermes() {
        List<Ferme> fermes = fermeService.getAllFermes();
        return new ResponseEntity<>(fermes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ferme> getFermeById(@PathVariable Long id) {
        try {
            Ferme ferme = fermeService.getFermeById(id);
            return new ResponseEntity<>(ferme, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/by-name")
    public ResponseEntity<Ferme> getFermeByName(@RequestParam String nom) {
        Optional<Ferme> ferme = fermeService.getFermeByName(nom);
        return ferme.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search/by-location")
    public ResponseEntity<Ferme> getFermeByLocation(@RequestParam String localisation) {
        try {
            Ferme ferme = fermeService.getFermeByLocalisation(localisation);
            return new ResponseEntity<>(ferme, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ferme> updateFerme(@PathVariable Long id, @RequestBody Ferme fermeDetails) {
        try {
            Ferme existingFerme = fermeService.getFermeById(id);
            existingFerme.setName(fermeDetails.getName());
            existingFerme.setLocation(fermeDetails.getLocation());
            existingFerme.setTotalArea(fermeDetails.getTotalArea());
            existingFerme.setCreationDate(fermeDetails.getCreationDate());

            Ferme updatedFerme = fermeService.saveFerme(existingFerme);
            return new ResponseEntity<>(updatedFerme, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFerme(@PathVariable Long id) {
        try {
            fermeService.deleteFerme(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
