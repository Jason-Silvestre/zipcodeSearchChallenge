package br.com.silvestre.zipcodeSearch.service;

import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchRequest;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;

import java.util.List;

public interface ZipcodeSearchService {
    ZipcodeSearchResponse searchZipcode(String cep);

    List<ZipcodeSearchRequest> getSearchHistory();
}
