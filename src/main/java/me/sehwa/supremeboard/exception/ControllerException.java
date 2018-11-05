package me.sehwa.supremeboard.exception;

public class ControllerException extends RuntimeException {

    public ControllerException() {
    }

    public ControllerException(RuntimeException e) {
        super(e);
    }
}
