package com.pedro.petcare.repository;

import com.pedro.petcare.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    // Más adelante podremos añadir métodos personalizados si los necesitamos
}
