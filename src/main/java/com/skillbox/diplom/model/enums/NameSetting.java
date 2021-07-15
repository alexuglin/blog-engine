package com.skillbox.diplom.model.enums;

public enum NameSetting {
    MULTIUSER_MODE ("Многопользовательский режим"),
    POST_PREMODERATION ("Премодерация постов"),
    STATISTICS_IS_PUBLIC ("Показывать всем статистику блога");

    private final String value;

    NameSetting(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
