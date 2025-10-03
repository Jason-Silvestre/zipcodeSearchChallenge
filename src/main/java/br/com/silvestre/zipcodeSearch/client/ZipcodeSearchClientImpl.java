package br.com.silvestre.zipcodeSearch.client;

import br.com.silvestre.zipcodeSearch.exception.ApiIntegrationException;
import br.com.silvestre.zipcodeSearch.exception.InvalidZipCodeFormatException;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;
import org.springframework.web.client.RestTemplate;

public class ZipcodeSearchClientImpl implements ZipcodeSearchClient {

    private final RestTemplate restTemplate;

    public ZipcodeSearchClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ZipcodeSearchResponse searchZipcode(String cep) throws ApiIntegrationException, InvalidZipCodeFormatException {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            ZipcodeSearchResponse response = restTemplate.getForObject(url, ZipcodeSearchResponse.class);

            if (response == null) {
                throw new ApiIntegrationException("No response from ViaCEP API");
            }

            return response;

        } catch (Exception e) {
            throw new ApiIntegrationException("Error integrating with ViaCEP API: " + e.getMessage());
        }
    }
}