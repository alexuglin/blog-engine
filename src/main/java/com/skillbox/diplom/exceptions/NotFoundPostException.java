package com.skillbox.diplom.exceptions;

public class NotFoundPostException extends RuntimeException {

    private final String message;

    public NotFoundPostException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
