package me.sehwa.supremeboard.exception;

public class ServiceException extends RuntimeException {

    public ServiceException() {
    }

    public ServiceException(RuntimeException e) {
        super(e);
    }
}
