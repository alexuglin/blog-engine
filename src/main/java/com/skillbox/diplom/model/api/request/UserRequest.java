package com.skillbox.diplom.model.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @JsonProperty("e_mail")
    private String email;

    @ToString.Exclude
    private String password;

    private String name;

    private String captcha;

    @JsonProperty("captcha_secret")
    private String captchaSecret;

    private MultipartFile photo;

    private boolean removePhoto;
}
