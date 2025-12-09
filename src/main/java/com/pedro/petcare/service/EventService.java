package com.pedro.petcare.service;

import com.pedro.petcare.model.Event;
import com.pedro.petcare.dto.TimelineItemDto;

import java.util.List;

public interface EventService {

    Event createEvent(Long petId, Event event);

    List<TimelineItemDto> getTimelineForPet(Long petId);
}
