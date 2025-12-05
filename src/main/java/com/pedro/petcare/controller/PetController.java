package com.pedro.petcare.controller;

import com.pedro.petcare.model.Pet;
import com.pedro.petcare.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar mascotas (Pets).
 * Expone endpoints bajo /api/pets para operaciones CRUD.
 */
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor // Inyecta petService mediante constructor generado por Lombok
public class PetController {

    // Servicio que contiene la lógica de negocio para Pet
    private final PetService petService;

    /**
     * GET /api/pets
     * Devuelve la lista de todas las mascotas.
     */
    @GetMapping
    public List<Pet> getAllPets() {
        return petService.findAll();
    }

    /**
     * GET /api/pets/{id}
     * Devuelve una mascota por su id.
     * @param id identificador de la mascota
     */
    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.findById(id);
    }

    /**
     * POST /api/pets
     * Crea una nueva mascota. Valida el cuerpo de la petición con @Valid.
     * Devuelve 201 Created con la entidad creada.
     * @param pet objeto Pet proveniente del cuerpo de la petición
     */
    @PostMapping
    public ResponseEntity<Pet> createPet(@Valid @RequestBody Pet pet) {
        Pet created = petService.create(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/pets/{id}
     * Actualiza una mascota existente.
     * @param id identificador de la mascota a actualizar
     * @param pet datos para la actualización (validados)
     */
    @PutMapping("/{id}")
    public Pet updatePet(@PathVariable Long id, @Valid @RequestBody Pet pet) {
        return petService.update(id, pet);
    }

    /**
     * DELETE /api/pets/{id}
     * Elimina una mascota por su id. Devuelve 204 No Content.
     * @param id identificador de la mascota a eliminar
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
