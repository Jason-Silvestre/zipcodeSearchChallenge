package br.com.silvestre.zipcodeSearch.controller;

import br.com.silvestre.zipcodeSearch.Exception.InvalidZipcodeException;
import br.com.silvestre.zipcodeSearch.Exception.ZipcodeNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidZipcodeException.class)
    public ResponseEntity<Map<String, String>> handleInvalidZipcode(InvalidZipcodeException ex) {
        Map<String, String> error = Map.of(
                "error", "invalid_zipcode",
                "message", "Invalid digit quantity",
                "details", "Zipcode must contain exactly 8 numeric digits"

        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ZipcodeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleZipcodeNotFound(ZipcodeNotFoundException ex) {
        //LOG EXCEPTION FOR DEBUGGING
        ex.printStackTrace();

        Map<String, String> error = Map.of(
                "error", "zipcode_not_found",
                "message", "Zipcode not found in database"

        );
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        //LOG EXCEPTION FOR DEBUGGING
        ex.printStackTrace();

        Map<String, String> error = Map.of(
                "error", "internal_server_error",
                "message", "An unexpected error occurred"
        );
        return ResponseEntity.internalServerError().body(error);
    }
}
