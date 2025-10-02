package br.com.silvestre.zipcodeSearch.client;

import br.com.silvestre.zipcodeSearch.dto.ViaCepResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * CLIENT FOR THE VIACEP API CONSUMPTION.
 * RESPONSIBLE FOR SEARCHING DE ZIPCODE INFORMATIONS IN THE EXTERNAL VIACEP API.
 */
@Component
public class ViaCepClient {

    private static final Logger logger = LoggerFactory.getLogger(ViaCepClient.class);

    private final RestTemplate restTemplate;

    @Value("${viacep.url:https://viacep.com.br}")
    private String viaCepUrl;

    /**
     * Construtor com injeção de dependência do RestTemplate
     * @param restTemplate RestTemplate configurado pelo Spring
     */
    public ViaCepClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Busca informações de um CEP na API ViaCEP
     *
     * @param zipCode CEP a ser consultado (apenas números, 8 dígitos)
     * @return ViaCepResponseDTO com os dados do endereço ou null se não encontrado
     * @throws RuntimeException se ocorrer erro na comunicação com a API
     */
    public ViaCepResponseDTO searchZipCode(String zipCode) {
        logger.info("Consultando CEP: {}", zipCode);

        String url = viaCepUrl + "/ws/" + zipCode + "/json/";

        try {
            ViaCepResponseDTO response = restTemplate.getForObject(url, ViaCepResponseDTO.class);

            if (response != null && Boolean.TRUE.equals(response.getErro())) {
                logger.warn("CEP não encontrado: {}", zipCode);
                return null;
            }

            logger.info("CEP encontrado: {} - {}", zipCode, response != null ? response.getLogradouro() : "N/A");
            return response;

        } catch (RestClientException e) {
            logger.error("Erro ao consultar API ViaCEP para o CEP: {}. Erro: {}", zipCode, e.getMessage());
            throw new RuntimeException("Erro ao consultar API ViaCEP para o CEP: " + zipCode, e);
        }
    }

    /**
     * Método utilitário para validar formato do CEP
     *
     * @param zipCode CEP a ser validado
     * @return true se o CEP é válido (8 dígitos numéricos)
     */
    public boolean isValidZipCodeFormat(String zipCode) {
        return zipCode != null && zipCode.matches("\\d{8}");
    }

    /**
     * Método para verificar se a API está disponível
     *
     * @return true se a API está respondendo
     */
    public boolean isApiAvailable() {
        try {
            String testUrl = viaCepUrl + "/ws/01001000/json/";
            restTemplate.getForObject(testUrl, ViaCepResponseDTO.class);
            return true;
        } catch (Exception e) {
            logger.warn("API ViaCEP não está disponível: {}", e.getMessage());
            return false;
        }
    }
}