package com.skillbox.diplom.exceptions;

import lombok.ToString;

@ToString
public class NotFoundDocumentException extends RuntimeException {

    private final String message;

    public NotFoundDocumentException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
