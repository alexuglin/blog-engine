package com.skillbox.diplom.exceptions.enums;

import com.skillbox.diplom.exceptions.NotFoundValue;

import java.util.Arrays;

public enum Errors {

    EMAIL("email", "Этот e-mail уже зарегистрирован"),
    NAME("name", "Имя указано неверно"),
    PASSWORD("password", "Пароль короче 6-ти символов"),
    CAPTCHA("captcha", "Код с картинки введён неверно"),
    PHOTO("photo", "Фото слишком большое, нужно не более 5 Мб"),
    TITLE("title", "Заголовок не установлен или слишком короткий"),
    TEXT("text", "Текст публикации не задан или слишком короткий"),
    IMAGE("image", "Размер файла превышает допустимый размер"),
    FILE_FORMAT("fileFormat", "Неверный формат файла(jpg, png)"),
    COMMENT("comment", "Текст комментария не задан или слишком короткий"),
    POST_ID("postId", "Публикации с таким номером не существует"),
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

    public static Errors findErrorsByFieldName(String fieldName) {
        return Arrays
                .stream(values())
                .filter(v -> v.getFieldName().equals(fieldName))
                .findFirst()
                .orElseThrow(() -> new NotFoundValue("Not found fieldName " + fieldName));
    }
}
