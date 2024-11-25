package com.example.citronix.service.Impl;
import com.example.citronix.domain.entities.Arbre;
import com.example.citronix.domain.entities.Champ;
import com.example.citronix.exception.*;
import com.example.citronix.repository.ArbreRepository;
import com.example.citronix.service.ChampService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

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
        champ.setArea(1000.0);  // Example area in square meters

        arbre = new Arbre();
        arbre.setId(1L);
        arbre.setChamp(champ);
        arbre.setPlantingDate(LocalDate.parse("2024-05-10"));
    }

    // Test for saving an Arbre with a valid champ and valid planting period
    @Test
    void testSaveArbre_Success() {
        when(champService.findById(1L)).thenReturn(champ);
        when(arbreRepository.save(any(Arbre.class))).thenReturn(arbre);

        Arbre savedArbre = arbreService.save(arbre);

        assertNotNull(savedArbre);
        verify(arbreRepository, times(1)).save(any(Arbre.class));
    }

    // Test for saving an Arbre when Champ does not exist
    @Test
    void testSaveArbre_ChampNotFound() {
        when(champService.findById(1L)).thenReturn(null);  // Champ is not found

        assertThrows(EntityNotFoundException.class, () -> arbreService.save(arbre));
    }

    // Test for saving an Arbre with invalid planting period
    @Test
    void testSaveArbre_InvalidPlantingPeriod() {
        when(champService.findById(1L)).thenReturn(champ);
        arbre.setPlantingDate(LocalDate.parse("2024-12-01"));  // Assume this is an invalid planting period

        assertThrows(InvalidPlantingPeriodException.class, () -> arbreService.save(arbre));
    }


    // Test for validating tree spacing in the champ
    @Test
    void testValidateTreeSpacing_Success() {
        when(champService.findById(1L)).thenReturn(champ);
        when(arbreRepository.countByChampId(1L)).thenReturn(100);  // Already 100 trees

        arbreService.validateTreeSpacing(champ, List.of(new Arbre(), new Arbre()));  // Add 2 more trees

        // No exception should be thrown
    }

    // Test for validating tree spacing when exceeding density
    @Test
    void testValidateTreeSpacing_ExceededDensity() {
        when(champService.findById(1L)).thenReturn(champ);
        when(arbreRepository.countByChampId(1L)).thenReturn(999);  // 999 trees already planted

        assertThrows(ExceededTreeDensityException.class, () ->
                arbreService.validateTreeSpacing(champ, List.of(new Arbre(), new Arbre())));  // Adding 2 more trees
    }

    // Test for finding an Arbre by ID that exists
    @Test
    void testFindById_Success() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));

        Arbre foundArbre = arbreService.findById(1L);

        assertNotNull(foundArbre);
        assertEquals(1L, foundArbre.getId());
    }

    // Test for finding an Arbre by ID that does not exist
    @Test
    void testFindById_ArbreNotFound() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> arbreService.findById(1L));
    }

    // Test for updating an Arbre successfully
    @Test
    void testUpdateArbre_Success() {
        Arbre updatedArbre = new Arbre();
        updatedArbre.setId(1L);
        updatedArbre.setPlantingDate(LocalDate.parse("2024-06-15"));

        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));
        when(champService.findById(1L)).thenReturn(champ);
        when(arbreRepository.save(any(Arbre.class))).thenReturn(updatedArbre);

        Arbre result = arbreService.update(1L, updatedArbre);

        assertNotNull(result);
        assertEquals("2024-06-15", result.getPlantingDate());
        verify(arbreRepository, times(1)).save(any(Arbre.class));
    }

    // Test for updating an Arbre that does not exist
    @Test
    void testUpdateArbre_ArbreNotFound() {
        Arbre updatedArbre = new Arbre();
        updatedArbre.setId(1L);

        when(arbreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> arbreService.update(1L, updatedArbre));
    }

    // Test for deleting an Arbre successfully
    @Test
    void testDeleteArbre_Success() {
        when(arbreRepository.existsById(1L)).thenReturn(true);

        arbreService.delete(1L);

        verify(arbreRepository, times(1)).deleteById(1L);
    }

    // Test for deleting an Arbre that does not exist
    @Test
    void testDeleteArbre_ArbreNotFound() {
        when(arbreRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> arbreService.delete(1L));
    }

    // Test for calculating age of an Arbre
    @Test
    void testCalculateAge() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));

        int age = arbreService.calculateAge(1L);

        assertEquals(0, age);  // Assume the tree is newly planted
    }

    // Test for calculating productivity of an Arbre
    @Test
    void testCalculateProductivity() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));

        double productivity = arbreService.calculateProductivity(1L);

        assertEquals(0.0, productivity);  // Assume the tree has no productivity for simplicity
    }
}
