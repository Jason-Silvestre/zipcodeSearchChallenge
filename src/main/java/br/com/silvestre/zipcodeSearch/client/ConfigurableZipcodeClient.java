package br.com.silvestre.zipcodeSearch.client;

import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Primary
public class ConfigurableZipcodeClient implements ZipcodeSearchClient {

    private final String apiBaseUrl;
    private final RestTemplate restTemplate;

    public ConfigurableZipcodeClient(
            @Value("${app.external-api.url:https://viacep.com.br}") String apiBaseUrl,
            RestTemplate restTemplate) {
        this.apiBaseUrl = apiBaseUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public ZipcodeSearchResponse searchZipcode(String cep) {
        String url = apiBaseUrl + "/ws/" + cep + "/json";
        try {
            return restTemplate.getForObject(url, ZipcodeSearchResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar CEP: " + e.getMessage());
        }
    }
}