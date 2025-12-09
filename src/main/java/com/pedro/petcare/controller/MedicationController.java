package com.pedro.petcare.controller;

import com.pedro.petcare.model.Medication;
import com.pedro.petcare.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    // Todas las medicaciones de una mascota
    @GetMapping("/pets/{petId}/medications")
    public List<Medication> getMedicationsByPet(@PathVariable Long petId) {
        return medicationService.getMedicationsByPet(petId);
    }

    // Crear medicación para una mascota
    @PostMapping("/pets/{petId}/medications")
    public ResponseEntity<Medication> createMedication(
            @PathVariable Long petId,
            @RequestBody Medication medication) {

        Medication created = medicationService.createMedication(petId, medication);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Actualizar medicación
    @PutMapping("/medications/{id}")
    public Medication updateMedication(
            @PathVariable Long id,
            @RequestBody Medication medication) {
        return medicationService.updateMedication(id, medication);
    }

    // Borrar medicación
    @DeleteMapping("/medications/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.noContent().build();
    }

    // Medicaciones atrasadas
    @GetMapping("/medications/overdue")
    public List<Medication> getOverdueMedications() {
        return medicationService.getOverdueMedications();
    }

    // Medicaciones próximas en X minutos
    @GetMapping("/medications/upcoming")
    public List<Medication> getUpcomingMedications(
            @RequestParam(defaultValue = "60") int minutes) {
        return medicationService.getUpcomingMedications(minutes);
    }
}
