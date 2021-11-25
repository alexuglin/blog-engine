package com.skillbox.diplom.controller;

import com.skillbox.diplom.model.DTO.CaptchaDTO;
import com.skillbox.diplom.model.api.request.UserRequest;
import com.skillbox.diplom.model.api.response.AuthResponse;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthService authService;

    @Autowired
    public ApiAuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/check")
    public ResponseEntity<AuthResponse> checkAuth() {
        return authService.checkAuth();
    }

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaDTO> getCaptcha() {
        return authService.getCaptcha();
    }

    @PostMapping("/register")
    public ResponseEntity<ErrorResponse> registerUser(@RequestBody UserRequest userRequest) {
        return authService.registerUser(userRequest);
    }
}