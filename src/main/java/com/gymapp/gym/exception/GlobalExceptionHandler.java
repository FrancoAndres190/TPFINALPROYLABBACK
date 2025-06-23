package com.gymapp.gym.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja NotFoundException > responde 404
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(ChangeSetPersister.NotFoundException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Maneja error > responde 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception e) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");

        // Captura el método donde falló
        StackTraceElement[] stackTrace = e.getStackTrace();
        String methodName = stackTrace.length > 0 ? stackTrace[0].getMethodName() : "Unknown Method";
        String fullMessage = "Error interno en el método " + methodName + ": " + e.getMessage();

        body.put("message", fullMessage);

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
