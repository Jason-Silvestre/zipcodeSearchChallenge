package br.com.silvestre.zipcodeSearch.ServiceTests;

import br.com.silvestre.zipcodeSearch.Exception.InvalidZipcodeException;
import br.com.silvestre.zipcodeSearch.Exception.ZipcodeNotFoundException;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchRequest;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;
import br.com.silvestre.zipcodeSearch.repository.ZipcodeSearchRepository;
import br.com.silvestre.zipcodeSearch.client.ZipcodeSearchClient;
import br.com.silvestre.zipcodeSearch.service.ZipcodeSearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ZipcodeSearchApplicationTests {

    @Mock
    private ZipcodeSearchClient zipcodeSearchClient;

    @Mock
    private ZipcodeSearchRepository zipcodeSearchRepository;

    @InjectMocks
    private ZipcodeSearchServiceImpl zipcodeSearchService;

    private ZipcodeSearchResponse validResponse;
    private ZipcodeSearchResponse notFoundResponse;

    @BeforeEach
    void setUp() {
        // Configura resposta válida
        validResponse = new ZipcodeSearchResponse();
        validResponse.setCep("01001000");
        validResponse.setLogradouro("Praça da Sé");
        validResponse.setBairro("Sé");
        validResponse.setLocalidade("São Paulo");
        validResponse.setUf("SP");
        validResponse.setErro(false);

        // Configura resposta para CEP não encontrado
        notFoundResponse = new ZipcodeSearchResponse();
        notFoundResponse.setErro(true);
    }

    @Test
    void shouldSearchZipcodeSuccessfully() {
        // Arrange
        String cep = "01001000";
        when(zipcodeSearchClient.searchZipcode(anyString())).thenReturn(validResponse);
        when(zipcodeSearchRepository.save(any(ZipcodeSearchRequest.class))).thenReturn(new ZipcodeSearchRequest());

        // Act
        ZipcodeSearchResponse result = zipcodeSearchService.searchZipcode(cep);

        // Assert
        assertNotNull(result);
        assertEquals("01001000", result.getCep());
        assertEquals("São Paulo", result.getLocalidade());

        verify(zipcodeSearchClient).searchZipcode("01001000");
        verify(zipcodeSearchRepository).save(any(ZipcodeSearchRequest.class));
    }

    @Test
    void shouldThrowExceptionWhenZipcodeHasInvalidLength() {
        // Arrange
        String invalidCep = "123";

        // Act & Assert
        InvalidZipcodeException exception = assertThrows(InvalidZipcodeException.class,
                () -> zipcodeSearchService.searchZipcode(invalidCep));

        assertEquals("Invalid zipcode format. It should contain exactly 8 digits.", exception.getMessage());
        verify(zipcodeSearchClient, never()).searchZipcode(anyString());
        verify(zipcodeSearchRepository, never()).save(any(ZipcodeSearchRequest.class));
    }

    @Test
    void shouldThrowExceptionWhenZipcodeHasMoreThan8Digits() {
        // Arrange
        String invalidCep = "123456789";

        // Act & Assert
        assertThrows(InvalidZipcodeException.class,
                () -> zipcodeSearchService.searchZipcode(invalidCep));
    }

    @Test
    void shouldThrowExceptionWhenZipcodeIsNull() {
        // Act & Assert
        assertThrows(InvalidZipcodeException.class,
                () -> zipcodeSearchService.searchZipcode(null));
    }

    @Test
    void shouldThrowExceptionWhenZipcodeHasLetters() {
        // Arrange
        String cepWithLetters = "ABC12345";

        // Act & Assert
        assertThrows(InvalidZipcodeException.class,
                () -> zipcodeSearchService.searchZipcode(cepWithLetters));
    }

    @Test
    void shouldThrowExceptionWhenZipcodeNotFound() {
        // Arrange
        String cep = "00000000";
        when(zipcodeSearchClient.searchZipcode(anyString())).thenReturn(notFoundResponse);

        // Act & Assert
        ZipcodeNotFoundException exception = assertThrows(ZipcodeNotFoundException.class,
                () -> zipcodeSearchService.searchZipcode(cep));

        assertEquals("Zipcode not found.", exception.getMessage());
        verify(zipcodeSearchClient).searchZipcode("00000000");
        verify(zipcodeSearchRepository, never()).save(any(ZipcodeSearchRequest.class));
    }

    @Test
    void shouldCleanZipcodeWithSpecialCharacters() {
        // Arrange
        String cepWithSpecialChars = "01.001-000";
        when(zipcodeSearchClient.searchZipcode(anyString())).thenReturn(validResponse);
        when(zipcodeSearchRepository.save(any(ZipcodeSearchRequest.class))).thenReturn(new ZipcodeSearchRequest());

        // Act
        ZipcodeSearchResponse result = zipcodeSearchService.searchZipcode(cepWithSpecialChars);

        // Assert
        assertNotNull(result);
        verify(zipcodeSearchClient).searchZipcode("01001000"); // Verifica que foi limpo
    }

    @Test
    void shouldGetSearchHistory() {
        // Arrange
        ZipcodeSearchRequest request1 = new ZipcodeSearchRequest("01001000", "response1");
        ZipcodeSearchRequest request2 = new ZipcodeSearchRequest("22030001", "response2");
        List<ZipcodeSearchRequest> expectedHistory = Arrays.asList(request1, request2);

        when(zipcodeSearchRepository.findAllByOrderByRequestTimeDesc()).thenReturn(expectedHistory);

        // Act
        List<ZipcodeSearchRequest> result = zipcodeSearchService.getSearchHistory();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(zipcodeSearchRepository).findAllByOrderByRequestTimeDesc();
    }
}