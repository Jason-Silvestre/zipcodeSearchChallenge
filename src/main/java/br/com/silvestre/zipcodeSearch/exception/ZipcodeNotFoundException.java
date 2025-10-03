package br.com.silvestre.zipcodeSearch.exception;

public class ZipcodeNotFoundException extends RuntimeException {
    public ZipcodeNotFoundException(String message) {
        super(message);
    }
}
