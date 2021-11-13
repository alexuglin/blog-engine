package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.CaptchaCode;
import com.skillbox.diplom.model.DTO.CaptchaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(imports = LocalDateTime.class)
public interface CaptchaMapper {

    @Mapping(target = "secret", source = "secretCode")
    @Mapping(target = "image", expression = "java(\"data:image/png;base64, \" + image)")
    CaptchaDTO convertToCaptchaDTO(String image, String secretCode);

    @Mapping(target = "time", expression = "java(LocalDateTime.now())")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "secretCode", source = "secretCode")
    CaptchaCode convertToCaptchaCode(String code, String secretCode);

}
