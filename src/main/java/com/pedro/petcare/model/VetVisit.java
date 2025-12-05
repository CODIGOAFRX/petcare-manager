package com.pedro.petcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "vet_visits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VetVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    @JsonIgnore // evitamos bucles o respuestas enormes; ya pasamos el petId por la URL
    private Pet pet;

    @PastOrPresent //@PastOrPresent → la fecha no puede ser futura (tiene sentido para visitas).
    @Column(nullable = false)
    private LocalDateTime visitDate;

    @NotBlank
    @Column(nullable = false)
    private String reason;  // motivo

    @Column(length = 1000)
    private String notes;

    private String clinic;
}
