package com.amazon.ata.music.playlist.service.exceptions;

public class RuntimeException extends java.lang.RuntimeException {
    public RuntimeException(Throwable cause) {
        super(cause);
    }
    public RuntimeException (String message, Throwable cause) {
        super(message, cause);
    }
    public RuntimeException (String message) {
        super(message);
    }
    public RuntimeException () {
        super();
    }
}
