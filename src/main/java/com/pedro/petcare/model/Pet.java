package com.pedro.petcare.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidad JPA que representa una mascota (Pet).
 *
 * Contiene validaciones básicas de datos y mapeo a la tabla "pets".
 */
@Entity
@Table(name = "pets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    /** Identificador único generado por la base de datos. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre de la mascota. No puede estar en blanco. */
    @NotBlank
    @Column(nullable = false)
    private String name;

    /** Especie de la mascota (ej. DOG, CAT). Se guarda como texto. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Species species;

    /** Raza (opcional). */
    private String breed;

    /** Peso en kilogramos. Debe ser positivo cuando se provee. */
    @Positive
    private Double weightKg;

    /** Fecha de nacimiento. Debe ser una fecha pasada. */
    @Past
    private LocalDate birthDate;

    /** Notas adicionales sobre la mascota (por ejemplo, alergias, observaciones). */
    @Column(length = 1000)
    private String notes;
}
