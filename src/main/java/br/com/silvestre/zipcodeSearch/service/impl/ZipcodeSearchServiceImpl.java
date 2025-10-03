package br.com.silvestre.zipcodeSearch.service.impl;

import br.com.silvestre.zipcodeSearch.exception.ApiIntegrationException;
import br.com.silvestre.zipcodeSearch.exception.InvalidZipCodeFormatException;
import br.com.silvestre.zipcodeSearch.exception.InvalidZipcodeException;
import br.com.silvestre.zipcodeSearch.exception.ZipcodeNotFoundException;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchRequest;
import br.com.silvestre.zipcodeSearch.repository.ZipcodeSearchRepository;
import br.com.silvestre.zipcodeSearch.client.ZipcodeSearchClient;
import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;
import br.com.silvestre.zipcodeSearch.service.ZipcodeSearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZipcodeSearchServiceImpl implements ZipcodeSearchService {

    private final ZipcodeSearchClient zipcodeSearchClient;
    private final ZipcodeSearchRepository zipcodeSearchRepository;

    public ZipcodeSearchServiceImpl(ZipcodeSearchClient zipcodeSearchClient,
                                    ZipcodeSearchRepository zipcodeSearchRepository) {
        this.zipcodeSearchClient = zipcodeSearchClient;
        this.zipcodeSearchRepository = zipcodeSearchRepository;
    }

    public ZipcodeSearchResponse searchZipcode(String cep) throws ApiIntegrationException, InvalidZipCodeFormatException {

        //CLEAN ZIPCODE (removes punctuation and special characters)
        String cleanedZipcode = cleanZipcode(cep);

        //VALIDATE DIGIT QUANTITY
        if(!isValidLength(cleanedZipcode)) {
            throw new InvalidZipcodeException("Invalid zipcode format. It should contain exactly 8 digits.");
        }

        //CONSULT EXTERNAL API
        ZipcodeSearchResponse response = zipcodeSearchClient.searchZipcode(cleanedZipcode);

        //CHECK IF ZIPCODE EXISTS IN EXTERNAL DATABASE

        if(zipcodeNotExistent(response)){
            throw new ZipcodeNotFoundException("Zipcode not found.");
        }


        //DATABASE LOG
        ZipcodeSearchRequest requestLog = new ZipcodeSearchRequest(cleanedZipcode, response.toString());
        zipcodeSearchRepository.save(requestLog); // Save the log to the database (persistence)

        return response;
    }

    @Override
    public List<ZipcodeSearchRequest> getSearchHistory() {
        return zipcodeSearchRepository.findAllByOrderByRequestTimeDesc();
    }

    private boolean zipcodeNotExistent(ZipcodeSearchResponse response) {
        return response == null ||
               response.getCep() == null ||
               response.getCep().isEmpty() ||
                (response.getErro() != null && response.getErro());
    }

    private String cleanZipcode(String zipcode) {
        if (zipcode == null) {
            return "";
        }
        return zipcode.replaceAll("\\D", ""); //REMOVES EVRYTHING THAT IS NOT A DIGIT
    }

    private boolean isValidLength(String zipcode) {
        return zipcode != null && zipcode.length() == 8; //VALID ZIPCODE MUST HAVE EXACTLY 8 DIGITS
    }
}
