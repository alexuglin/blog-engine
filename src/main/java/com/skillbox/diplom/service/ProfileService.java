package com.skillbox.diplom.service;

import com.skillbox.diplom.model.api.request.UserRequest;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final Logger logger = Logger.getLogger(ProfileService.class);

    public ResponseEntity<ErrorResponse> editProfile(UserRequest userRequest) {
        logger.info("editProfile: " + userRequest);
        return ResponseEntity.ok(new ErrorResponse());
    }
}
