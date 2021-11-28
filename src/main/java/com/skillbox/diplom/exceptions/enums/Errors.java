package com.skillbox.diplom.exceptions.enums;

public enum Errors {

    EMAIL("Этот e-mail уже зарегистрирован"),
    NAME("Имя указано неверно"),
    PASSWORD("Пароль короче 6-ти символов"),
    CAPTCHA("Код с картинки введён неверно"),
    TITLE("Заголовок не установлен"),
    POST("Текст публикации слишком короткий"),
    IMAGE("Размер файла превышает допустимый размер"),
    COMMENT("Текст комментария не задан или слишком короткий"),
    DOCUMENT_NOT_FOUND("Документ не найден");

    private final String message;

    Errors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
