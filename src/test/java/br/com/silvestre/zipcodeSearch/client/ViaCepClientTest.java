package br.com.silvestre.zipcodeSearch.client;

import br.com.silvestre.zipcodeSearch.Exception.ApiIntegrationException;
import br.com.silvestre.zipcodeSearch.Exception.InvalidZipCodeFormatException;
import br.com.silvestre.zipcodeSearch.dto.ViaCepResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ViaCepClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ViaCepClient viaCepClient;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(viaCepClient, "viaCepUrl", "https://viacep.com.br");
    }

    @Test
    void shouldReturnValidCep() throws ApiIntegrationException, InvalidZipCodeFormatException {
        // Setup
        ViaCepResponseDTO mockResponse = new ViaCepResponseDTO();
        mockResponse.setCep("01001-000");
        mockResponse.setLogradouro("Praça da Sé");
        mockResponse.setErro(false);

        when(restTemplate.getForObject(anyString(), eq(ViaCepResponseDTO.class)))
                .thenReturn(mockResponse);

        // Execute
        ViaCepResponseDTO result = viaCepClient.searchZipcode("01001000");

        // Verify
        assertNotNull(result);
        assertEquals("01001-000", result.getCep());
        assertEquals("Praça da Sé", result.getLogradouro());
        assertFalse(result.getErro());
    }

    @Test
    void shouldReturnNullForInvalidCep() throws ApiIntegrationException, InvalidZipCodeFormatException {
        // Setup
        ViaCepResponseDTO mockResponse = new ViaCepResponseDTO();
        mockResponse.setErro(true);

        when(restTemplate.getForObject(anyString(), eq(ViaCepResponseDTO.class)))
                .thenReturn(mockResponse);

        // Execute
        ViaCepResponseDTO result = viaCepClient.searchZipcode("99999999");

        // Verify
        assertNull(result);
    }

    @Test
    void shouldThrowExceptionOnApiFailure() {
        // Setup
        when(restTemplate.getForObject(anyString(), eq(ViaCepResponseDTO.class)))
                .thenThrow(new RestClientException("API error"));

        // Execute & Verify
        assertThrows(RuntimeException.class, () -> {
            viaCepClient.searchZipcode("01001000");
        });
    }
}