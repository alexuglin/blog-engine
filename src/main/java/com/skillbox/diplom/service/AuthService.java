package com.skillbox.diplom.service;

import com.skillbox.diplom.model.api.response.AuthResponse;
import com.skillbox.diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<AuthResponse> checkAuth() {
        return ResponseEntity.ok(new AuthResponse());
    }

}
