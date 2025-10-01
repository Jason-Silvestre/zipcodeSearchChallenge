package br.com.silvestre.zipcodeSearch.client;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;

@Component
public class ViaCepClient implements ZipcodeSearchClient {

    private final RestTemplate restTemplate;
    public ViaCepClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    @Override
    public ZipcodeSearchResponse searchZipcode(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, ZipcodeSearchResponse.class);
    }

}
