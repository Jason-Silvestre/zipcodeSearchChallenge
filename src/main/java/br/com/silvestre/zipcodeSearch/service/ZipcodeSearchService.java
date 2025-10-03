package br.com.silvestre.zipcodeSearch.service;

import br.com.silvestre.zipcodeSearch.exception.ApiIntegrationException;
import br.com.silvestre.zipcodeSearch.exception.InvalidZipCodeFormatException;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchRequest;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;

import java.util.List;

public interface ZipcodeSearchService {
    ZipcodeSearchResponse searchZipcode(String zipCode) throws ApiIntegrationException, InvalidZipCodeFormatException;
    List<ZipcodeSearchRequest> getSearchHistory();
    ZipcodeSearchRequest saveSearch(String cep, String responseData);
}