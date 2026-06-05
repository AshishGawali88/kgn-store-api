package com.kgn.store.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    /** Turn validation failures into a clean 400 with field messages. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fields = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fields.putIfAbsent(fe.getField(), fe.getDefaultMessage());
        }
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "validation_failed");
        body.put("fields", fields);
        return ResponseEntity.badRequest().body(body);
    }

    /** Catch-all: never expose internal details to the client. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOther(Exception ex) {
        // Log full detail server-side only.
        org.slf4j.LoggerFactory.getLogger(ApiExceptionHandler.class)
                .error("Unhandled error", ex);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "server_error");
        body.put("message", "Something went wrong. Please try again.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
