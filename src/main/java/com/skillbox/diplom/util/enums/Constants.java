package com.skillbox.diplom.util.enums;

public enum Constants {

    PERCENT ("%"),
    TAGS ("tags");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}