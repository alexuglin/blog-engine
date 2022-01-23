package com.skillbox.diplom.exceptions;

public class NotFoundValue extends RuntimeException {

    private final String message;

    public NotFoundValue(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
