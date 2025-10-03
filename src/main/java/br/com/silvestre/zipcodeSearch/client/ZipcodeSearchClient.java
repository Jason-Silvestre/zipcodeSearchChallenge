package br.com.silvestre.zipcodeSearch.client;

import br.com.silvestre.zipcodeSearch.exception.ApiIntegrationException;
import br.com.silvestre.zipcodeSearch.exception.InvalidZipCodeFormatException;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;

public interface ZipcodeSearchClient {
    ZipcodeSearchResponse searchZipcode(String cep) throws ApiIntegrationException, InvalidZipCodeFormatException;
}
