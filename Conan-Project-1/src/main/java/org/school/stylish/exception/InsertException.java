package org.school.stylish.exception;

public class InsertException extends RuntimeException {
    public InsertException(String message) {
        super(message);
    }

    public InsertException(String message, Throwable cause) {
        super(message, cause);
    }
}
