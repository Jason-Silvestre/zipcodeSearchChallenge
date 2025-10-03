package br.com.silvestre.zipcodeSearch.controller;

import br.com.silvestre.zipcodeSearch.Exception.ApiIntegrationException;
import br.com.silvestre.zipcodeSearch.Exception.InvalidZipCodeFormatException;
import br.com.silvestre.zipcodeSearch.Exception.InvalidZipcodeException;
import br.com.silvestre.zipcodeSearch.Exception.ZipcodeNotFoundException;

import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchResponse;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchRequest;
import br.com.silvestre.zipcodeSearch.service.ZipcodeSearchService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ceps")
public class ZipcodeSearchController {

    private final ZipcodeSearchService zipcodeSearchService;

    public ZipcodeSearchController(ZipcodeSearchService zipcodeSearchService) {
        this.zipcodeSearchService = zipcodeSearchService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<?> searchZipcode(@PathVariable String cep) throws ApiIntegrationException, InvalidZipCodeFormatException {
        try{
            ZipcodeSearchResponse response = zipcodeSearchService.searchZipcode(cep);
            return ResponseEntity.ok(response);

        } catch (InvalidZipcodeException | ZipcodeNotFoundException e) {
            //GLOBAL HANDLER
           throw e;
        } catch (Exception e) {
            //UNEXPECTED ERRORS
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "internal_server_error")
            );
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<ZipcodeSearchRequest>> getSearchHistory() {
       return ResponseEntity.ok(zipcodeSearchService.getSearchHistory());
    }

}
