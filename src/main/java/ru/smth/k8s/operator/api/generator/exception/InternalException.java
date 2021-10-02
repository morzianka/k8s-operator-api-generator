package ru.smth.k8s.operator.api.generator.exception;

/**
 * @author Shabunina Anita
 */
public class InternalException extends RuntimeException {

    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }
}
