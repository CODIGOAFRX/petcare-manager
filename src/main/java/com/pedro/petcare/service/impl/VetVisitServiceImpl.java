package com.pedro.petcare.service.impl;

import com.pedro.petcare.exception.ResourceNotFoundException;
import com.pedro.petcare.model.Pet;
import com.pedro.petcare.model.VetVisit;
import com.pedro.petcare.repository.PetRepository;
import com.pedro.petcare.repository.VetVisitRepository;
import com.pedro.petcare.service.VetVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VetVisitServiceImpl implements VetVisitService {

    private final VetVisitRepository vetVisitRepository;
    private final PetRepository petRepository;

    @Override
    public List<VetVisit> getVisitsByPet(Long petId) {
        return vetVisitRepository.findByPetIdOrderByVisitDateDesc(petId);
    }

    @Override
    public VetVisit getById(Long id) {
        return vetVisitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vet visit not found with id " + id));
    }

    @Override
    public VetVisit createVisit(Long petId, VetVisit vetVisit) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id " + petId));

        vetVisit.setId(null);
        vetVisit.setPet(pet);
        return vetVisitRepository.save(vetVisit);
    }

    @Override
    public VetVisit updateVisit(Long petId, Long visitId, VetVisit vetVisit) {
        VetVisit existing = getById(visitId);

        if (!existing.getPet().getId().equals(petId)) {
            throw new ResourceNotFoundException("Vet visit " + visitId + " does not belong to pet " + petId);
        }

        existing.setVisitDate(vetVisit.getVisitDate());
        existing.setReason(vetVisit.getReason());
        existing.setNotes(vetVisit.getNotes());
        existing.setClinic(vetVisit.getClinic());

        return vetVisitRepository.save(existing);
    }

    @Override
    public void deleteVisit(Long petId, Long visitId) {
        VetVisit existing = getById(visitId);

        if (!existing.getPet().getId().equals(petId)) {
            throw new ResourceNotFoundException("Vet visit " + visitId + " does not belong to pet " + petId);
        }

        vetVisitRepository.delete(existing);
    }
}
