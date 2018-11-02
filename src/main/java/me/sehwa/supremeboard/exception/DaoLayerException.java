package me.sehwa.supremeboard.exception;

public class DaoLayerException extends RuntimeException {

    private String errorMessage;
    private int errorCode;

    public DaoLayerException() {
    }

    public DaoLayerException(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
