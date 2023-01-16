package com.caiotfernandes.assembleiaCoop.services.exceptions;

public class ClosedSessionException extends RuntimeException {
    public ClosedSessionException(String message) {
        super(message);
    }
}
