package br.com.silvestre.zipcodeSearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ViaCepResponseDTO {

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("logradouro")
    private String logradouro;

    @JsonProperty("complemento")
    private String complemento;

    @JsonProperty("bairro")
    private String bairro;

    @JsonProperty("localidade")
    private String localidade;

    @JsonProperty("uf")
    private String uf;

    @JsonProperty("ibge")
    private String ibge;

    @JsonProperty("gia")
    private String gia;

    @JsonProperty("ddd")
    private String ddd;

    @JsonProperty("siafi")
    private String siafi;

    @JsonProperty("erro")
    private Boolean erro;

    // Constructors
    public ViaCepResponseDTO() {
    }

    // Getters e Setters
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }


    public Boolean getErro() { return erro; }
    public void setErro(Boolean erro) { this.erro = erro; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViaCepResponseDTO that = (ViaCepResponseDTO) o;
        return Objects.equals(cep, that.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cep);
    }

    @Override
    public String toString() {
        return "ViaCepResponseDTO{cep='" + cep + "', logradouro='" + logradouro + "', localidade='" + localidade + "'}";
    }
}