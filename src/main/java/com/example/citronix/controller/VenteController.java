package com.example.citronix.controller;

import com.example.citronix.domain.entities.Vente;
import com.example.citronix.service.VenteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventes")
public class VenteController {

    private final VenteService venteService;

    public VenteController(VenteService venteService) {
        this.venteService = venteService;
    }

    @PostMapping
    public ResponseEntity<Vente> createVente(@RequestBody Vente vente) {
        Vente createdVente = venteService.save(vente);
        return ResponseEntity.ok(createdVente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vente> getVenteById(@PathVariable Long id) {
        Vente vente = venteService.findById(id);
        return ResponseEntity.ok(vente);
    }

    @GetMapping
    public ResponseEntity<Page<Vente>> getAllVentes(Pageable pageable) {
        Page<Vente> ventes = venteService.findAll(pageable);
        return ResponseEntity.ok(ventes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vente> updateVente(@PathVariable Long id, @RequestBody Vente venteDetails) {
        Vente existingVente = venteService.findById(id);
        existingVente.setDate(venteDetails.getDate());
        existingVente.setUnitPrice(venteDetails.getUnitPrice());
        existingVente.setQuantity(venteDetails.getQuantity());
        existingVente.setClient(venteDetails.getClient());
        existingVente.setRevenue(venteDetails.getRevenue());
        existingVente.setRecolte(venteDetails.getRecolte());

        Vente updatedVente = venteService.save(existingVente);
        return ResponseEntity.ok(updatedVente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVente(@PathVariable Long id) {
        venteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/revenue")
    public ResponseEntity<Double> calculateRevenue(@PathVariable Long id) {
        Vente vente = venteService.findById(id);
        double revenue = vente.getQuantity() * vente.getUnitPrice();
        return ResponseEntity.ok(revenue);
    }
}
