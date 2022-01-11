package com.skillbox.diplom.controller;

import com.skillbox.diplom.model.DTO.CaptchaDTO;
import com.skillbox.diplom.model.api.request.UserRequest;
import com.skillbox.diplom.model.api.response.AuthResponse;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.model.validation.OnRegister;
import com.skillbox.diplom.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Validated
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApiAuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserRequest userRequest) {
        return authService.login(userRequest);
    }

    @GetMapping("/logout")
    public ResponseEntity<AuthResponse> logout() {
        return authService.logout();
    }

    @GetMapping("/check")
    public ResponseEntity<AuthResponse> checkAuth(Principal principal) {
        return authService.checkAuth(principal);
    }

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaDTO> getCaptcha() {
        return authService.getCaptcha();
    }

    @PostMapping("/register")
    @Validated(OnRegister.class)
    public ResponseEntity<ErrorResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        return authService.registerUser(userRequest);
    }

    @PostMapping("/restore")
    public ResponseEntity<ErrorResponse> restorePassword(@RequestBody UserRequest userRequest, HttpServletRequest httpServletRequest) {
        return authService.restorePassword(userRequest, httpServletRequest);
    }
}