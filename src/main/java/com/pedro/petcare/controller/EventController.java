package com.pedro.petcare.controller;

import com.pedro.petcare.model.Event;
import com.pedro.petcare.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets/{petId}/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(
            @PathVariable Long petId,
            @RequestBody Event event) {

        Event created = eventService.createEvent(petId, event);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
