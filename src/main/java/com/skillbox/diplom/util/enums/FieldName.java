package com.skillbox.diplom.util.enums;

public enum FieldName {

    TIME("time"),
    TAGS("tags"),
    ID("id");

    private final String description;

    FieldName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
