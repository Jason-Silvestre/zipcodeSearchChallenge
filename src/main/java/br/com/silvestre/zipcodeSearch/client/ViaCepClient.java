package br.com.silvestre.zipcodeSearch.client;

import br.com.silvestre.zipcodeSearch.exception.ApiIntegrationException;
import br.com.silvestre.zipcodeSearch.exception.InvalidZipCodeFormatException;
import br.com.silvestre.zipcodeSearch.exception.ZipcodeNotFoundException;
import br.com.silvestre.zipcodeSearch.dto.ViaCepResponseDTO;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * CLIENT FOR THE VIACEP API CONSUMPTION.
 * RESPONSIBLE FOR SEARCHING DE ZIPCODE INFORMATIONS IN THE EXTERNAL VIACEP API.
 */
@Component
public class ViaCepClient implements ZipcodeSearchClient {

    private static final Logger logger = LoggerFactory.getLogger(ViaCepClient.class);

    private final RestTemplate restTemplate;
    private final String viaCepUrl;

    public ViaCepClient(RestTemplate restTemplate,
                        @Value("${viacep.url:https://viacep.com.br}") String viaCepUrl) {
        this.restTemplate = restTemplate;
        this.viaCepUrl = viaCepUrl;
    }

    @Override
    public ZipcodeSearchResponse searchZipcode(String cep) throws ApiIntegrationException, InvalidZipCodeFormatException {
        validateZipCodeFormat(cep);
        logger.info("Consulting Zipcode: {}", cep);

        String url = viaCepUrl + "/ws/" + cep + "/json/";

        try {
            ViaCepResponseDTO viaCepResponse = restTemplate.getForObject(url, ViaCepResponseDTO.class);

            if (viaCepResponse == null || Boolean.TRUE.equals(viaCepResponse.getErro())) {
                logger.warn("Zipcode not found: {}", cep);
                throw new ZipcodeNotFoundException("Zipcode not found: " + cep);
            }

            logger.info("Zipcode found: {} - {}", cep, viaCepResponse.getLogradouro());
            return convertToZipcodeSearchResponse(viaCepResponse);

        } catch (RestClientException e) {
            logger.error("Error while consulting ViaCEP API for zipcode: {}", cep, e);
            throw new ApiIntegrationException("Error communicating with ViaCEP API");
        }
    }

    private void validateZipCodeFormat(String zipCode) throws InvalidZipCodeFormatException {
        if (zipCode == null || !zipCode.matches("\\d{8}")) {
            throw new InvalidZipCodeFormatException("Invalid zipcode format: " + zipCode);
        }
    }

    private ZipcodeSearchResponse convertToZipcodeSearchResponse(ViaCepResponseDTO viaCepResponse) {
        // Consider using ModelMapper for more complex conversions
        ZipcodeSearchResponse response = new ZipcodeSearchResponse();
        response.setCep(viaCepResponse.getCep());
        response.setLogradouro(viaCepResponse.getLogradouro());
        response.setErro(viaCepResponse.getErro());
        return response;
    }
}