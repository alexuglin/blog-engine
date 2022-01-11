package com.skillbox.diplom.exceptions.enums;

public enum Errors {

    DOCUMENT_NOT_FOUND("document", "Документ не найден");

    private final String fieldName;
    private final String message;

    Errors(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }

}
