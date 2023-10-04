package com.amazon.ata.music.playlist.service.exceptions;

public class InvalidAttributeChangeException extends RuntimeException{
    private static final long serialVersionUID = 5792791575434959720L;

    public InvalidAttributeChangeException() {
        super();
    }
    public InvalidAttributeChangeException(String message) {
        super(message);
    }
    public InvalidAttributeChangeException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidAttributeChangeException(Throwable cause) {
        super(cause);
    }
}
