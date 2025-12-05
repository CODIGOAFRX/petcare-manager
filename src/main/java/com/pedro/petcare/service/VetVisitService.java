package com.pedro.petcare.service;

import com.pedro.petcare.model.VetVisit;

import java.util.List;

public interface VetVisitService {

    List<VetVisit> getVisitsByPet(Long petId);

    VetVisit getById(Long id);

    VetVisit createVisit(Long petId, VetVisit vetVisit);

    VetVisit updateVisit(Long petId, Long visitId, VetVisit vetVisit);

    void deleteVisit(Long petId, Long visitId);
}
