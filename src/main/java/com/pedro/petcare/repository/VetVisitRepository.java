package com.pedro.petcare.repository;

import com.pedro.petcare.model.VetVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetVisitRepository extends JpaRepository<VetVisit, Long> {

    List<VetVisit> findByPetIdOrderByVisitDateDesc(Long petId);
}
