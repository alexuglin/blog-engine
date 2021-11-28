package com.skillbox.diplom.service;

import com.skillbox.diplom.exceptions.WrongDataException;
import com.skillbox.diplom.exceptions.enums.Errors;
import com.skillbox.diplom.model.CaptchaCode;
import com.skillbox.diplom.model.DTO.CaptchaDTO;
import com.skillbox.diplom.model.User;
import com.skillbox.diplom.model.api.request.UserRequest;
import com.skillbox.diplom.model.api.response.AuthResponse;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.model.mappers.CaptchaMapper;
import com.skillbox.diplom.model.mappers.UserMapper;
import com.skillbox.diplom.repository.CaptchaCodeRepository;
import com.skillbox.diplom.repository.UserRepository;
import com.skillbox.diplom.util.GrayCase;
import com.skillbox.diplom.util.enums.FieldName;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CaptchaCodeRepository captchaCodeRepository;
    private final CaptchaMapper captchaMapper = Mappers.getMapper(CaptchaMapper.class);
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final Logger logger = Logger.getLogger(AuthService.class);
    private final GrayCase grayCase = new GrayCase();

    public ResponseEntity<AuthResponse> checkAuth() {
        /* ToDo Сделать возвращение информации о текущем авторизованном пользователе,
            если он авторизован. Он должен проверять, сохранён ли идентификатор
            текущей сессии в списке авторизованных. */
        return ResponseEntity.ok(new AuthResponse());
    }

    @Transactional
    public ResponseEntity<CaptchaDTO> getCaptcha() {
        String tokenForImage = grayCase.getTokenGenerator().next();
        String tokenForSecretCode = grayCase.getTokenGenerator().next() + LocalDateTime.now();
        String secretCode = Base64.getEncoder().encodeToString(tokenForSecretCode.getBytes()).substring(0, 20);
        CaptchaCode captchaCode = captchaMapper.convertToCaptchaCode(tokenForImage, secretCode);
        captchaCodeRepository.save(captchaCode);
        byte[] imageBytes = grayCase.draw(tokenForImage);
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        CaptchaDTO captchaDTO = captchaMapper.convertToCaptchaDTO(encodedImage, secretCode);
        logger.info("Generate captcha successes");
        return ResponseEntity.ok(captchaDTO);
    }

    @Transactional
    public ResponseEntity<ErrorResponse> registerUser(UserRequest userRequest) {
        logger.info(userRequest);
        Map<String, String> errors = validationUserRequest(userRequest);
        ErrorResponse errorResponse = new ErrorResponse();
        if (!errors.isEmpty()) {
            errorResponse.setErrors(errors);
            errorResponse.setResult(false);
            throw new WrongDataException(errorResponse);
        }
        User user = userMapper.userRequestToUser(userRequest);
        userRepository.save(user);
        return ResponseEntity.ok(errorResponse);
    }

    public Map<String, String> validationUserRequest(UserRequest userRequest) {
        Map<String, String> errors = new HashMap<>();
        User user = userRepository.findByEmail(userRequest.getEmail());
        if (!Objects.isNull(user)) {
            errors.put(FieldName.EMAIL.getDescription(), Errors.EMAIL.getMessage());
        }
        CaptchaCode captchaCode = captchaCodeRepository.findBySecretCode(userRequest.getCaptchaSecret());
        if (!userRequest.getCaptcha().equals(captchaCode.getCode())) {
            errors.put(FieldName.CAPTCHA.getDescription(), Errors.CAPTCHA.getMessage());
        }
        if (userRequest.getName().isBlank()) {
            errors.put(FieldName.NAME.getDescription(), Errors.NAME.getMessage());
        }
        if (userRequest.getPassword().trim().length() < 6) {
            errors.put(FieldName.PASSWORD.getDescription(), Errors.PASSWORD.getMessage());
        }
        return errors;
    }
}
