package com.pedro.petcare.service;

import com.pedro.petcare.model.Pet;

import java.util.List;

public interface PetService {

    List<Pet> findAll();

    Pet findById(Long id);

    Pet create(Pet pet);

    Pet update(Long id, Pet pet);

    void delete(Long id);
}
