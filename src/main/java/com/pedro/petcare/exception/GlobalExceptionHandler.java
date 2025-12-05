package com.pedro.petcare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de excepciones global para la capa REST de la aplicación.
 *
 * <p>Esta clase, anotada con @RestControllerAdvice, centraliza el manejo de excepciones
 * lanzadas por los controladores y transforma las mismas en respuestas HTTP con un
 * cuerpo estructurado (JSON) que contiene información útil para el cliente:
 * - timestamp: fecha y hora de la excepción
 * - status: código HTTP
 * - error: descripción corta del tipo de error
 * - message / messages: detalle del error o listado de errores de validación</p>
 *
 * <p>Permite mantener un formato uniforme de respuestas de error y evita duplicación
 * de código en los controladores individuales.</p>
 */
 
/**
 * Maneja ResourceNotFoundException lanzadas por los controladores.
 *
 * <p>Construye una ResponseEntity con status 404 (NOT_FOUND) y un cuerpo que incluye:
 * - timestamp: momento en que se produjo la excepción
 * - status: 404
 * - error: "Not Found"
 * - message: mensaje de la excepción proporcionado desde la capa de servicio/controlador</p>
 *
 * @param ex la excepción ResourceNotFoundException capturada
 * @return ResponseEntity con el cuerpo de error y el código HTTP 404
 */
 
/**
 * Maneja MethodArgumentNotValidException provocadas por validaciones de parámetros
 * (por ejemplo, @Valid en objetos de petición).
 *
 * <p>Construye una ResponseEntity con status 400 (BAD_REQUEST) y un cuerpo que incluye:
 * - timestamp: momento en que se produjo la excepción
 * - status: 400
 * - error: "Validation error"
 * - messages: un mapa campo -> mensaje que agrupa los errores de validación por campo</p>
 *
 * <p>Este método extrae los errores de BindingResult y los agrupa en un Map<String, String>,
 * facilitando al cliente la presentación o el manejo detallado de cada campo inválido.</p>
 *
 * @param ex la excepción MethodArgumentNotValidException que contiene los errores de validación
 * @return ResponseEntity con el cuerpo de error y el código HTTP 400
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation error");

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e ->
                errors.put(e.getField(), e.getDefaultMessage())
        );
        body.put("messages", errors);

        return ResponseEntity.badRequest().body(body);
    }
}
