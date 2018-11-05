package me.sehwa.supremeboard.exception;

public class RepositoryException extends RuntimeException {

    public RepositoryException() {
    }

    public RepositoryException(RuntimeException e) {
        super(e);
    }
}
