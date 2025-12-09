package com.pedro.petcare.service.impl;

import com.pedro.petcare.dto.TimelineItemDto;
import com.pedro.petcare.model.Event;
import com.pedro.petcare.model.Medication;
import com.pedro.petcare.model.Pet;
import com.pedro.petcare.model.VetVisit;
import com.pedro.petcare.repository.EventRepository;
import com.pedro.petcare.repository.MedicationRepository;
import com.pedro.petcare.repository.VetVisitRepository;
import com.pedro.petcare.service.EventService;
import com.pedro.petcare.service.PetService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final VetVisitRepository vetVisitRepository;
    private final MedicationRepository medicationRepository;
    private final PetService petService;

    public EventServiceImpl(EventRepository eventRepository,
                            VetVisitRepository vetVisitRepository,
                            MedicationRepository medicationRepository,
                            PetService petService) {
        this.eventRepository = eventRepository;
        this.vetVisitRepository = vetVisitRepository;
        this.medicationRepository = medicationRepository;
        this.petService = petService;
    }

    @Override
    public Event createEvent(Long petId, Event event) {
        Pet pet = petService.getPetById(petId); // adapta nombre de método si hace falta

        event.setId(null);
        event.setPet(pet);

        if (event.getEventDate() == null) {
            event.setEventDate(LocalDateTime.now());
        }

        return eventRepository.save(event);
    }

    @Override
    public List<TimelineItemDto> getTimelineForPet(Long petId) {
        // Comprobamos que la mascota existe
        petService.getPetById(petId); // si no existe, lanzará ResourceNotFoundException

        List<TimelineItemDto> items = new ArrayList<>();

        // Visitas veterinarias
        List<VetVisit> visits = vetVisitRepository.findByPetIdOrderByVisitDateDesc(petId);
        for (VetVisit visit : visits) {
            items.add(TimelineItemDto.builder()
                    .type("VET_VISIT")
                    .date(visit.getVisitDate())
                    .data(visit)
                    .build());
        }

        // Eventos
        List<Event> events = eventRepository.findByPetIdOrderByEventDateDesc(petId);
        for (Event e : events) {
            items.add(TimelineItemDto.builder()
                    .type("EVENT")
                    .date(e.getEventDate())
                    .data(e)
                    .build());
        }

        // Medicaciones (usamos nextDoseAt como fecha)
        List<Medication> meds = medicationRepository.findByPetId(petId);
        for (Medication m : meds) {
            if (m.getNextDoseAt() != null) {
                items.add(TimelineItemDto.builder()
                        .type("MEDICATION")
                        .date(m.getNextDoseAt())
                        .data(m)
                        .build());
            }
        }

        // Ordenamos por fecha ascendente
        items.sort(Comparator.comparing(TimelineItemDto::getDate));

        return items;
    }
}
