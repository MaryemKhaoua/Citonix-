package com.example.citronix.service.Impl;
import com.example.citronix.domain.entities.Arbre;
import com.example.citronix.domain.entities.Champ;
import com.example.citronix.exception.*;
import com.example.citronix.repository.ArbreRepository;
import com.example.citronix.service.ChampService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import jakarta.persistence.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class ArbreServiceImplTest {

    @Mock
    private ArbreRepository arbreRepository;

    @Mock
    private ChampService champService;

    @InjectMocks
    private ArbreServiceImpl arbreService;

    private Arbre arbre;
    private Champ champ;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        champ = new Champ();
        champ.setId(1L);
        champ.setArea(1000.0);

        arbre = new Arbre();
        arbre.setId(1L);
        arbre.setChamp(champ);
        arbre.setPlantingDate(LocalDate.parse("2024-05-10"));
    }

    @Test
    void testSaveArbre_Success() {
        when(champService.findById(1L)).thenReturn(champ);
        when(arbreRepository.save(any(Arbre.class))).thenReturn(arbre);

        Arbre savedArbre = arbreService.save(arbre);

        assertNotNull(savedArbre);
        verify(arbreRepository, times(1)).save(any(Arbre.class));
    }

    @Test
    void testSaveArbre_ChampNotFound() {
        when(champService.findById(1L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> arbreService.save(arbre));
    }

    @Test
    void testSaveArbre_InvalidPlantingPeriod() {
        when(champService.findById(1L)).thenReturn(champ);
        arbre.setPlantingDate(LocalDate.parse("2024-12-01"));

        assertThrows(InvalidPlantingPeriodException.class, () -> arbreService.save(arbre));
    }


    @Test
    void testValidateTreeSpacing_Success() {
        when(champService.findById(1L)).thenReturn(champ);
        when(arbreRepository.countByChampId(1L)).thenReturn(100);

        arbreService.validateTreeSpacing(champ, List.of(new Arbre(), new Arbre()));

    }


    @Test
    void testFindById_Success() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));

        Arbre foundArbre = arbreService.findById(1L);

        assertNotNull(foundArbre);
        assertEquals(1L, foundArbre.getId());
    }

    @Test
    void testFindById_ArbreNotFound() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> arbreService.findById(1L));
    }



    @Test
    void testUpdateArbre_ArbreNotFound() {
        Arbre updatedArbre = new Arbre();
        updatedArbre.setId(1L);

        when(arbreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> arbreService.update(1L, updatedArbre));
    }

    @Test
    void testDeleteArbre_Success() {
        when(arbreRepository.existsById(1L)).thenReturn(true);

        arbreService.delete(1L);

        verify(arbreRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteArbre_ArbreNotFound() {
        when(arbreRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> arbreService.delete(1L));
    }

    @Test
    void testCalculateAge() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));

        int age = arbreService.calculateAge(1L);

        assertEquals(0, age);
    }
}
