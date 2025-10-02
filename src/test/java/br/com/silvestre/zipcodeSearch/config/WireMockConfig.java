package br.com.silvestre.zipcodeSearch.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@TestConfiguration
public class WireMockConfig {

    @Bean
    @Primary
    public WireMockServer mockViaCepService() {
        WireMockServer wireMockServer = new WireMockServer(8089);
        wireMockServer.start();

        // Mock para CEP válido
        wireMockServer.stubFor(get(urlEqualTo("/ws/01001000/json"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("""
                            {
                                "cep": "01001-000",
                                "logradouro": "Praça da Sé",
                                "complemento": "lado ímpar",
                                "bairro": "Sé",
                                "localidade": "São Paulo",
                                "uf": "SP",
                                "ibge": "3550308",
                                "gia": "1004",
                                "ddd": "11",
                                "siafi": "7107"
                            }
                        """)));

        // Mock para CEP não encontrado
        wireMockServer.stubFor(get(urlEqualTo("/ws/00000000/json"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("""
                            {
                                "erro": true
                            }
                        """)));

        return wireMockServer;
    }
}