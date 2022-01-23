package com.skillbox.diplom.util.enums;

public enum FieldName {

    MESSAGE("message"),
    TIME("time"),
    TAGS("tags"),
    CAPTCHA("captcha"),
    ID("id");

    private final String description;

    FieldName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
