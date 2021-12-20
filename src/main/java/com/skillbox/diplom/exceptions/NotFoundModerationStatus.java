package com.skillbox.diplom.exceptions;

public class NotFoundModerationStatus extends RuntimeException {

    private final String message;

    public NotFoundModerationStatus(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
