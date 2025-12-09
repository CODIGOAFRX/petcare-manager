package com.pedro.petcare.repository;

import com.pedro.petcare.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    // Todas las medicaciones de una mascota
    List<Medication> findByPetId(Long petId);

    // Medicaciones activas con próxima toma ANTES de una fecha (atrasadas)
    List<Medication> findByActiveTrueAndNextDoseAtBefore(LocalDateTime dateTime);

    // Medicaciones activas con próxima toma ENTRE dos momentos (próximas en X minutos)
    List<Medication> findByActiveTrueAndNextDoseAtBetween(LocalDateTime from, LocalDateTime to);
}