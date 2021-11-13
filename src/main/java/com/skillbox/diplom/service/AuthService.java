package com.skillbox.diplom.service;

import com.skillbox.diplom.model.CaptchaCode;
import com.skillbox.diplom.model.DTO.CaptchaDTO;
import com.skillbox.diplom.model.api.response.AuthResponse;
import com.skillbox.diplom.model.mappers.CaptchaMapper;
import com.skillbox.diplom.repository.CaptchaCodeRepository;
import com.skillbox.diplom.repository.UserRepository;
import com.skillbox.diplom.util.UCase;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CaptchaCodeRepository captchaCodeRepository;
    private final CaptchaMapper captchaMapper = Mappers.getMapper(CaptchaMapper.class);
    private final Logger logger = Logger.getLogger(AuthService.class);
    private final UCase uCase = new UCase();

    public ResponseEntity<AuthResponse> checkAuth() {
        /* ToDo Сделать возвращение информации о текущем авторизованном пользователе,
            если он авторизован. Он должен проверять, сохранён ли идентификатор
            текущей сессии в списке авторизованных. */
        return ResponseEntity.ok(new AuthResponse());
    }

    @Transactional
    public ResponseEntity<CaptchaDTO> getCaptcha() {
        String tokenForImage = uCase.getTokenGenerator().next();
        String tokenForSecretCode = uCase.getTokenGenerator().next() + LocalDateTime.now();
        String secretCode = Base64.getEncoder().encodeToString(tokenForSecretCode.getBytes()).substring(20);
        CaptchaCode captchaCode = captchaMapper.convertToCaptchaCode(tokenForImage, secretCode);
        captchaCodeRepository.save(captchaCode);
        byte[] imageBytes = uCase.draw(tokenForImage);
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        CaptchaDTO captchaDTO = captchaMapper.convertToCaptchaDTO(encodedImage, secretCode);
        logger.info("Generate captcha successes");
        return ResponseEntity.ok(captchaDTO);
    }
}
