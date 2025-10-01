package br.com.silvestre.zipcodeSearch.Exception;

public class ZipcodeNotFoundException extends RuntimeException {
    public ZipcodeNotFoundException(String message) {
        super(message);
    }
}
