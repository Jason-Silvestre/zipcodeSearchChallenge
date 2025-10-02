package br.com.silvestre.zipcodeSearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * DTO FOR MAPPING RESPONSE TO ViaCEP API
 */
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

    @JsonProperty("erro")
    private Boolean erro;

    // Construtor padrão necessário para Jackson
    public ViaCepResponseDTO() {
    }

    // Construtor com parâmetros
    public ViaCepResponseDTO(String cep, String logradouro, String complemento,
                             String bairro, String localidade, String uf, Boolean erro) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.erro = erro;
    }

    // Getters and Setters
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Boolean getErro() {
        return erro;
    }

    public void setErro(Boolean erro) {
        this.erro = erro;
    }

    // equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViaCepResponseDTO that = (ViaCepResponseDTO) o;
        return Objects.equals(cep, that.cep) &&
                Objects.equals(logradouro, that.logradouro) &&
                Objects.equals(complemento, that.complemento) &&
                Objects.equals(bairro, that.bairro) &&
                Objects.equals(localidade, that.localidade) &&
                Objects.equals(uf, that.uf) &&
                Objects.equals(erro, that.erro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cep, logradouro, complemento, bairro, localidade, uf, erro);
    }

    // toString
    @Override
    public String toString() {
        return "ViaCepResponseDTO{" +
                "cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", localidade='" + localidade + '\'' +
                ", uf='" + uf + '\'' +
                ", erro=" + erro +
                '}';
    }

    /**
     * Verifica se a resposta é válida (CEP foi encontrado)
     * @return true se o CEP foi encontrado, false caso contrário
     */
    public boolean isValid() {
        return erro == null || !erro;
    }

    /**
     * CONVERT TO STRING TO LAOD IN THE DATABASE
     * @return FORMATED STRING FOR PERSISTENCE
     */
    public String toResponseData() {
        return String.format("CEP: %s, Logradouro: %s, Bairro: %s, Cidade: %s, UF: %s",
                cep, logradouro, bairro, localidade, uf);
    }
}