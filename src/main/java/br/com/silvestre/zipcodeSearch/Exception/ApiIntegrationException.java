package br.com.silvestre.zipcodeSearch.Exception;

import org.springframework.web.client.RestClientException;

public class ApiIntegrationException extends Throwable {
    public ApiIntegrationException(String errorCommunicatingWithViaCEPApi, RestClientException e) {
    }
}
