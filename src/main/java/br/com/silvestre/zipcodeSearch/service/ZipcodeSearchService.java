package br.com.silvestre.zipcodeSearch.service;

import br.com.silvestre.zipcodeSearch.exception.ApiIntegrationException;
import br.com.silvestre.zipcodeSearch.exception.InvalidZipCodeFormatException;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchRequest;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ZipcodeSearchService {
    ZipcodeSearchResponse searchZipcode(String zipCode) throws ApiIntegrationException, InvalidZipCodeFormatException;

    List<ZipcodeSearchRequest> getSearchHistory();
}
