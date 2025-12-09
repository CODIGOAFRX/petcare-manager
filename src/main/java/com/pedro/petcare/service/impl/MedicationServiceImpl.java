package com.pedro.petcare.service.impl;

import com.pedro.petcare.exception.ResourceNotFoundException;
import com.pedro.petcare.model.Medication;
import com.pedro.petcare.model.Pet;
import com.pedro.petcare.repository.MedicationRepository;
import com.pedro.petcare.service.MedicationService;
import com.pedro.petcare.service.PetService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final PetService petService;

    //constructor para inyección de dependencias
    public MedicationServiceImpl(MedicationRepository medicationRepository,
                                 PetService petService) {
        this.medicationRepository = medicationRepository; // inicializa el repositorio de medicamentos
        this.petService = petService;
    }

    //obtiene la lista de medicamentos asociados a una mascota específica
    @Override
    public List<Medication> getMedicationsByPet(Long petId) {
        return medicationRepository.findByPetId(petId);
    }

    //crea un nuevo registro de medicamento para una mascota específica
    @Override
    public Medication createMedication(Long petId, Medication medication) {
        Pet pet = petService.getPetById(petId); 
        medication.setPet(pet);
        if (medication.getNextDoseAt() == null) {
            medication.setNextDoseAt(LocalDateTime.now());
        }
        return medicationRepository.save(medication);
    } 

    

    //actualiza un registro de medicamento existente
    @Override
    public Medication updateMedication(Long id, Medication updated) {
        Medication existing = medicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication not found with id " + id));

        existing.setName(updated.getName());
        existing.setDose(updated.getDose());
        existing.setFrequency(updated.getFrequency());
        existing.setNextDoseAt(updated.getNextDoseAt());
        existing.setActive(updated.isActive());

        return medicationRepository.save(existing);
    }

    //elimina un registro de medicamento por su ID
    @Override
    public void deleteMedication(Long id) {
        if (!medicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medication not found with id " + id);
        }
        medicationRepository.deleteById(id);
    }

    //obtiene la lista de medicamentos que están activos y cuya próxima dosis está atrasada
    @Override
    public List<Medication> getOverdueMedications() {
        return medicationRepository.findByActiveTrueAndNextDoseAtBefore(LocalDateTime.now());
    }

    //obtiene la lista de medicamentos que tendrán una dosis próxima dentro de un rango de minutos especificado
    @Override
    public List<Medication> getUpcomingMedications(int minutesAhead) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime to = now.plusMinutes(minutesAhead);
        return medicationRepository.findByActiveTrueAndNextDoseAtBetween(now, to);
    }
}
