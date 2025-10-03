package br.com.silvestre.zipcodeSearch.exception;

public class InvalidZipcodeException extends RuntimeException {
    public InvalidZipcodeException(String message) {
        super(message);
    }
}
