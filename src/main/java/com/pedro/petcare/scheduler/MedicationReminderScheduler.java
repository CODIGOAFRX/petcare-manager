package com.pedro.petcare.scheduler;

import com.pedro.petcare.model.Medication;
import com.pedro.petcare.service.MedicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MedicationReminderScheduler {

    private final MedicationService medicationService;

    /**
     * Ventana de tiempo en minutos para considerar medicaciones "próximas".
     * Se puede configurar en application.properties:
     *
     * petcare.medication.upcoming-window-minutes=60
     */
    @Value("${petcare.medication.upcoming-window-minutes:60}")
    private int upcomingWindowMinutes;

    /**
     * Tarea programada que se ejecuta periódicamente.
     *
     * fixedRateString = intervalo entre ejecuciones (en milisegundos).
     * Aquí por defecto 300000 ms = 5 minutos, pero se puede sobreescribir:
     *
     * petcare.medication.check-interval-ms=300000
     */
    @Scheduled(fixedRateString = "${petcare.medication.check-interval-ms:300000}")
    public void checkMedications() {
        log.info("Ejecutando comprobación de medicaciones...");

        // 1) Medicaciones atrasadas
        List<Medication> overdue = medicationService.getOverdueMedications();
        if (!overdue.isEmpty()) {
            log.warn("Se han encontrado {} medicaciones ATRASADAS:", overdue.size());
            overdue.forEach(m ->
                    log.warn(" - [{}] {} para la mascota ID={}, próxima dosis en {} (ATRASADA)",
                            m.getId(), m.getName(),
                            m.getPet().getId(),
                            m.getNextDoseAt())
            );
        } else {
            log.info("No hay medicaciones atrasadas.");
        }

        // 2) Medicaciones próximas
        List<Medication> upcoming = medicationService.getUpcomingMedications(upcomingWindowMinutes);
        if (!upcoming.isEmpty()) {
            log.info("Se han encontrado {} medicaciones PRÓXIMAS en los próximos {} minutos:",
                    upcoming.size(), upcomingWindowMinutes);

            upcoming.forEach(m ->
                    log.info(" - [{}] {} para la mascota ID={}, próxima dosis en {}",
                            m.getId(), m.getName(),
                            m.getPet().getId(),
                            m.getNextDoseAt())
            );
        } else {
            log.info("No hay medicaciones próximas en los próximos {} minutos.", upcomingWindowMinutes);
        }

        log.info("Comprobación de medicaciones finalizada.");
    }
}
