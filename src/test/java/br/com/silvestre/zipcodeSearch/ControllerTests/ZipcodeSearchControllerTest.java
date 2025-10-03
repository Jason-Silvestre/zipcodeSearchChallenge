package br.com.silvestre.zipcodeSearch.ControllerTests;

import br.com.silvestre.zipcodeSearch.Exception.ApiIntegrationException;
import br.com.silvestre.zipcodeSearch.Exception.InvalidZipCodeFormatException;
import br.com.silvestre.zipcodeSearch.Exception.InvalidZipcodeException;
import br.com.silvestre.zipcodeSearch.Exception.ZipcodeNotFoundException;
import br.com.silvestre.zipcodeSearch.controller.ZipcodeSearchController;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchRequest;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;
import br.com.silvestre.zipcodeSearch.service.ZipcodeSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ZipcodeSearchControllerTest {
    @Mock
    private ZipcodeSearchService zipcodeSearchService;

    @InjectMocks
    private ZipcodeSearchController zipcodeSearchController;

    private ZipcodeSearchResponse validResponse;

    @BeforeEach
    void setUp() {
        validResponse = new ZipcodeSearchResponse();
        validResponse.setCep("01001000");
        validResponse.setLogradouro("Praça da Sé");
        validResponse.setLocalidade("São Paulo");
        validResponse.setUf("SP");
    }

    @Test
    void shouldReturnZipcodeSuccessfully() throws ApiIntegrationException, InvalidZipCodeFormatException {
        // Arrange
        String cep = "01001000";
        when(zipcodeSearchService.searchZipcode(cep)).thenReturn(validResponse);

        // Act
        ResponseEntity<?> response = zipcodeSearchController.searchZipcode(cep);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(validResponse, response.getBody());
        verify(zipcodeSearchService).searchZipcode(cep);
    }

    @Test
    void shouldPropagateInvalidZipcodeException() throws ApiIntegrationException, InvalidZipCodeFormatException {
        // Arrange
        String invalidCep = "123";
        when(zipcodeSearchService.searchZipcode(invalidCep))
                .thenThrow(new InvalidZipcodeException("Invalid zipcode format"));

        // Act & Assert
        assertThrows(InvalidZipcodeException.class,
                () -> zipcodeSearchController.searchZipcode(invalidCep));
    }

    @Test
    void shouldPropagateZipcodeNotFoundException() throws ApiIntegrationException, InvalidZipCodeFormatException {
        // Arrange
        String notFoundCep = "00000000";
        when(zipcodeSearchService.searchZipcode(notFoundCep))
                .thenThrow(new ZipcodeNotFoundException("Zipcode not found"));

        // Act & Assert
        assertThrows(ZipcodeNotFoundException.class,
                () -> zipcodeSearchController.searchZipcode(notFoundCep));
    }

    @Test
    void shouldReturnSearchHistory() {
        // Arrange
        ZipcodeSearchRequest request1 = new ZipcodeSearchRequest("01001000", "response1");
        ZipcodeSearchRequest request2 = new ZipcodeSearchRequest("22030001", "response2");
        List<ZipcodeSearchRequest> history = Arrays.asList(request1, request2);

        when(zipcodeSearchService.getSearchHistory()).thenReturn(history);

        // Act
        ResponseEntity<List<ZipcodeSearchRequest>> response = zipcodeSearchController.getSearchHistory();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(zipcodeSearchService).getSearchHistory();
    }
}

