package com.pedro.petcare.controller;

import com.pedro.petcare.model.VetVisit;
import com.pedro.petcare.service.VetVisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets/{petId}/visits")
@RequiredArgsConstructor
public class VetVisitController {

    private final VetVisitService vetVisitService;

    @GetMapping
    public List<VetVisit> getVisitsByPet(@PathVariable Long petId) {
        return vetVisitService.getVisitsByPet(petId);
    }

    @PostMapping
    public ResponseEntity<VetVisit> createVisit(@PathVariable Long petId,
                                                @Valid @RequestBody VetVisit vetVisit) {
        VetVisit created = vetVisitService.createVisit(petId, vetVisit);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{visitId}")
    public VetVisit updateVisit(@PathVariable Long petId,
                                @PathVariable Long visitId,
                                @Valid @RequestBody VetVisit vetVisit) {
        return vetVisitService.updateVisit(petId, visitId, vetVisit);
    }

    @DeleteMapping("/{visitId}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long petId,
                                            @PathVariable Long visitId) {
        vetVisitService.deleteVisit(petId, visitId);
        return ResponseEntity.noContent().build();
    }
}
