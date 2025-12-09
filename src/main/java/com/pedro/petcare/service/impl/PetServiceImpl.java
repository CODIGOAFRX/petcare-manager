package com.pedro.petcare.service.impl;

import com.pedro.petcare.exception.ResourceNotFoundException;
import com.pedro.petcare.model.Pet;
import com.pedro.petcare.repository.PetRepository;
import com.pedro.petcare.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public Pet getPetById(Long id) {
    return petRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id " + id));
}


    @Override
    public Pet create(Pet pet) {
        pet.setId(null); // aseguramos que se crea una nueva
        return petRepository.save(pet);
    }

    @Override
    public Pet update(Long id, Pet pet) {
        Pet existing = getPetById(id); // lanza excepción si no existe

        existing.setName(pet.getName());
        existing.setSpecies(pet.getSpecies());
        existing.setBreed(pet.getBreed());
        existing.setWeightKg(pet.getWeightKg());
        existing.setBirthDate(pet.getBirthDate());
        existing.setNotes(pet.getNotes());

        return petRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Pet existing = getPetById(id);
        petRepository.delete(existing);
    }
}
