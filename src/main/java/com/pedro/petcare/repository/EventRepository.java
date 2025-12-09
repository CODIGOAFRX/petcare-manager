package com.pedro.petcare.repository;

import com.pedro.petcare.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    // Todos los eventos de una mascota, ordenados del más reciente al más antiguo
    List<Event> findByPetIdOrderByEventDateDesc(Long petId);
}
