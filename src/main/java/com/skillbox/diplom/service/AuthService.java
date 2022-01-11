package com.skillbox.diplom.service;

import com.skillbox.diplom.config.AppSecurityConfig;
import com.skillbox.diplom.model.CaptchaCode;
import com.skillbox.diplom.model.DTO.CaptchaDTO;
import com.skillbox.diplom.model.DTO.UserDTO;
import com.skillbox.diplom.model.api.request.UserRequest;
import com.skillbox.diplom.model.api.response.AuthResponse;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.model.enums.ModerationStatus;
import com.skillbox.diplom.model.mappers.CaptchaMapper;
import com.skillbox.diplom.model.mappers.ResponseMapper;
import com.skillbox.diplom.model.mappers.UserMapper;
import com.skillbox.diplom.repository.CaptchaCodeRepository;
import com.skillbox.diplom.repository.PostRepository;
import com.skillbox.diplom.repository.UserRepository;
import com.skillbox.diplom.util.GenerateHash;
import com.skillbox.diplom.util.GrayCase;
import com.skillbox.diplom.util.UtilResponse;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CaptchaCodeRepository captchaCodeRepository;
    private final CaptchaMapper captchaMapper = Mappers.getMapper(CaptchaMapper.class);
    private final ResponseMapper responseMapper = Mappers.getMapper(ResponseMapper.class);
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final Logger logger = Logger.getLogger(AuthService.class);
    private final GrayCase grayCase = new GrayCase();
    private final ApplicationContext applicationContext;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<AuthResponse> login(UserRequest userRequest) {
        logger.info("login: " + userRequest);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
        User user = (User) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(getAuthResponse(user.getUsername()));
    }

    public ResponseEntity<AuthResponse> logout() {
        SecurityContextHolder.clearContext();
        AuthResponse authResponse = new AuthResponse();
        authResponse.setResult(true);
        return ResponseEntity.ok(authResponse);
    }

    public ResponseEntity<AuthResponse> checkAuth(Principal principal) {
        AuthResponse authResponse = Objects.isNull(principal) ? new AuthResponse() : getAuthResponse(principal.getName());
        return ResponseEntity.ok(authResponse);
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
        logger.info("registerUser: " + userRequest);
        AppSecurityConfig appSecurityConfig = applicationContext.getBean(AppSecurityConfig.class);
        userRequest.setPassword(appSecurityConfig.passwordEncoder().encode(userRequest.getPassword()));
        com.skillbox.diplom.model.User user = userMapper.userRequestToUser(userRequest);
        userRepository.save(user);
        return ResponseEntity.ok(new ErrorResponse());
    }

    private AuthResponse getAuthResponse(String email) {
        com.skillbox.diplom.model.User currentUser = userRepository
                .findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        int moderationCount = currentUser.isModerator() ?
                postRepository.countPostByModerationStatusAndIsActive(ModerationStatus.NEW, true) : 0;
        UserDTO userDTO = userMapper.convertTo(currentUser, moderationCount);
        return responseMapper.convertTo(userDTO, true);
    }

    @Transactional
    public ResponseEntity<ErrorResponse> restorePassword(UserRequest userRequest, HttpServletRequest httpServletRequest) {
        String email = userRequest.getEmail();
        logger.info("restorePassword request email: " + email);
        Optional<com.skillbox.diplom.model.User> optionalUser = Objects.nonNull(email) && !email.isBlank() ?
                userRepository.findByEmail(email) : Optional.empty();
        if (optionalUser.isEmpty()) {
            return ResponseEntity.ok(UtilResponse.getErrorResponse());
        }
        com.skillbox.diplom.model.User user = optionalUser.get();
        String hash = GenerateHash.getHash();
        String link = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(),
                "/login/change-password/" + hash);
        user.setCode(hash);
        userRepository.save(user);
        logger.info("sent to " + link);
        return ResponseEntity.ok(new ErrorResponse());
    }
}
