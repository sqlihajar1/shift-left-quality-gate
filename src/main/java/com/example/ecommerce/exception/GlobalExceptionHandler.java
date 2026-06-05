package com.example.ecommerce.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Gestion des ressources non trouvées (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(404, ex.getMessage());
    }
    // Gestion des requêtes invalides personnalisées (400)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException ex) {
        return buildResponse(400, ex.getMessage());
    }
    // Gestion des validations @Valid pour les @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", 400);
        Map<String, String> messages = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e ->
                messages.put(e.getField(), e.getDefaultMessage())
        );
        error.put("error", messages);
        return ResponseEntity.status(400).body(error);
    }
    // Gestion des validations sur les entités directement (@NotBlank, @NotNull)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", 400);
        Map<String, String> messages = new HashMap<>();
        ex.getConstraintViolations().forEach(v ->
                messages.put(v.getPropertyPath().toString(), v.getMessage())
        );
        error.put("error", messages);
        return ResponseEntity.status(400).body(error);
    }
    // Gestion générique des autres exceptions (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception ex) {
        return buildResponse(500, ex.getMessage());
    }
    // Méthode utilitaire pour construire la réponse JSON
    private ResponseEntity<Map<String, Object>> buildResponse(int status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status);
        error.put("error", message);
        return ResponseEntity.status(status).body(error);
    }
}
