package com.skillbox.diplom.util.enums;

public enum FieldName {

    TIME("time"),
    IS_ACTIVE("isActive"),
    MODERATION_STATUS("moderationStatus"),
    NAME("name"),
    TAG("tag"),
    POST("post"),
    ;

    private final String description;

    FieldName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
