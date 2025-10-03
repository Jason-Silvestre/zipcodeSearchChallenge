package br.com.silvestre.zipcodeSearch.client;

import br.com.silvestre.zipcodeSearch.Exception.ApiIntegrationException;
import br.com.silvestre.zipcodeSearch.Exception.InvalidZipCodeFormatException;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;

public interface ZipcodeSearchClient {
    ZipcodeSearchResponse searchZipcode(String cep) throws ApiIntegrationException, InvalidZipCodeFormatException;
}
