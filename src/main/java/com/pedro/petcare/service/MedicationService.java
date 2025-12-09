package com.pedro.petcare.service;

import com.pedro.petcare.model.Medication;

import java.util.List;

public interface MedicationService {

    List<Medication> getMedicationsByPet(Long petId);

    Medication createMedication(Long petId, Medication medication);

    Medication updateMedication(Long id, Medication medication);

    void deleteMedication(Long id);

    List<Medication> getOverdueMedications();

    List<Medication> getUpcomingMedications(int minutesAhead);
}
