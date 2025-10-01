package br.com.silvestre.zipcodeSearch.Exception;

public class InvalidZipcodeException extends RuntimeException {
    public InvalidZipcodeException(String message) {
        super(message);
    }
}
