package com.example.citronix.service.Impl;

import com.example.citronix.domain.entities.Recolte;
import com.example.citronix.domain.entities.Vente;
import com.example.citronix.repository.RecolteRepository;
import com.example.citronix.repository.VenteRepository;
import com.example.citronix.service.VenteService;
import com.example.citronix.exception.InsufficientRecolteQuantityException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VenteServiceImpl implements VenteService {

    private final VenteRepository venteRepository;
    private final RecolteRepository recolteRepository;

    public VenteServiceImpl(VenteRepository venteRepository, RecolteRepository recolteRepository) {
        this.venteRepository = venteRepository;
        this.recolteRepository = recolteRepository;
    }

    @Override
    public Vente save(Vente vente) {
        Recolte recolte = recolteRepository.findById(vente.getRecolte().getId())
                .orElseThrow(() -> new EntityNotFoundException("Récolte introuvable avec l'id : " + vente.getRecolte().getId()));

        if (recolte.getTotalQuantity() < vente.getQuantity()) {
            throw new InsufficientRecolteQuantityException("Quantité insuffisante dans la récolte !");
        }

        recolte.setTotalQuantity(recolte.getTotalQuantity() - vente.getQuantity());
        recolteRepository.save(recolte);

        double revenue = vente.getQuantity() * vente.getUnitPrice();
        vente.setRevenue(revenue);

        return venteRepository.save(vente);
    }

    @Override
    public Vente findById(Long id) {
        return venteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vente introuvable avec l'id : " + id));
    }

    @Override
    public Page<Vente> findAll(Pageable pageable) {
        return venteRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        if (!venteRepository.existsById(id)) {
            throw new EntityNotFoundException("Vente introuvable avec l'id : " + id);
        }
        venteRepository.deleteById(id);
    }
}
