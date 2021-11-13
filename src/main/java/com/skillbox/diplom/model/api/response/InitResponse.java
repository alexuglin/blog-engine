package com.skillbox.diplom.model.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InitResponse {

    @JsonProperty(value = "title")
    private String title = "DevPub";

    @JsonProperty(value = "subtitle")
    private String subtitle = "Рассказы разработчиков";

    @JsonProperty(value = "phone")
    private String phone = "+7-900-539 95 79";

    @JsonProperty(value = "email")
    private String email = "auglin@yandex.ru";

    @JsonProperty(value = "copyright")
    private String copyright = "Углин Александр";

    @JsonProperty(value = "copyrightFrom")
    private String copyrightFrom = "2021";
}
