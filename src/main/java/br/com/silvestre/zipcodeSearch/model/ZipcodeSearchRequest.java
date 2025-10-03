package br.com.silvestre.zipcodeSearch.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "zipcode_search_request")
public class ZipcodeSearchRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cep;

    @Column(name = "request_time")
    private LocalDateTime requestTime;

    @Column(columnDefinition = "TEXT")
    private String responseData;

    public ZipcodeSearchRequest() {}

    public ZipcodeSearchRequest(String cep, String responseData) {
        this.cep = cep;
        this.requestTime = LocalDateTime.now();
        this.responseData = responseData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
