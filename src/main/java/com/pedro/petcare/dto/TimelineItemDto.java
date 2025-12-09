package com.pedro.petcare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TimelineItemDto {

    /** Tipo: VET_VISIT, EVENT, MEDICATION */
    private String type;

    /** Fecha asociada al ítem */
    private LocalDateTime date;

    /** Objeto asociado (visita, evento o medicación) */
    private Object data;
}
