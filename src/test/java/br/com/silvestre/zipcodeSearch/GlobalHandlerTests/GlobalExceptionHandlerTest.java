package br.com.silvestre.zipcodeSearch.GlobalHandlerTests;

import br.com.silvestre.zipcodeSearch.Exception.InvalidZipcodeException;
import br.com.silvestre.zipcodeSearch.Exception.ZipcodeNotFoundException;
import br.com.silvestre.zipcodeSearch.controller.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void shouldHandleInvalidZipcodeException() {
        // Arrange
        InvalidZipcodeException exception = new InvalidZipcodeException("Invalid digit quantity");

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleInvalidZipcode(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("invalid_zipcode", response.getBody().get("error"));
        assertEquals("Invalid digit quantity", response.getBody().get("message"));
    }

    @Test
    void shouldHandleZipcodeNotFoundException() {
        // Arrange
        ZipcodeNotFoundException exception = new ZipcodeNotFoundException("Zipcode not found");

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleZipcodeNotFound(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("zipcode_not_found", response.getBody().get("error"));
        assertEquals("Zipcode not found in database", response.getBody().get("message"));
    }

    @Test
    void shouldHandleGeneralException() {
        // Arrange
        Exception exception = new RuntimeException("Unexpected error");

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleGeneralException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("internal_server_error", response.getBody().get("error"));
        assertEquals("An unexpected error occurred", response.getBody().get("message"));
    }
}

