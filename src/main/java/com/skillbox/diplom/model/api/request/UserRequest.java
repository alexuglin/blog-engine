package com.skillbox.diplom.model.api.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillbox.diplom.model.validation.OnEditProfile;
import com.skillbox.diplom.model.validation.OnRegister;
import com.skillbox.diplom.model.validation.OnRestorePassword;
import com.skillbox.diplom.model.validation.annotation.ConstraintCaptcha;
import com.skillbox.diplom.model.validation.annotation.ConstraintImage;
import com.skillbox.diplom.model.validation.annotation.ConstraintName;
import com.skillbox.diplom.model.validation.annotation.ExistsEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConstraintCaptcha(groups = {OnRegister.class, OnRestorePassword.class}, message = "Код с картинки введён неверно")
public class UserRequest {

    @ExistsEmail(groups = {OnRegister.class, OnEditProfile.class}, message = "Этот e-mail уже зарегистрирован")
    @JsonAlias({"e_mail", "email"})
    private String email;

    @Size(min = 6, groups = {OnRegister.class, OnRestorePassword.class, OnEditProfile.class},
            message = "Пароль короче 6-ти символов")
    @ToString.Exclude
    private String password;

    @ConstraintName(groups = {OnRegister.class, OnEditProfile.class}, message = "Имя указано неверно")
    private String name;

    private String captcha;

    @JsonProperty("captcha_secret")
    private String captchaSecret;

    private String code;

    @ConstraintImage(groups = OnEditProfile.class)
    private Object photo;

    private boolean removePhoto;
}
