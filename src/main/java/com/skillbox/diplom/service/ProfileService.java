package com.skillbox.diplom.service;

import com.skillbox.diplom.config.AppSecurityConfig;
import com.skillbox.diplom.exceptions.WrongDataException;
import com.skillbox.diplom.model.User;
import com.skillbox.diplom.model.api.request.UserRequest;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.repository.UserRepository;
import com.skillbox.diplom.util.UserUtility;
import com.skillbox.diplom.util.UtilResponse;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(ProfileService.class);
    private final UserUtility userUtility;
    private final ApplicationContext applicationContext;
    private final StorageService storageService;
    private static final int PHOTO_WEIGHT = 36;
    private static final int PHOTO_HEIGHT = 36;

    @Transactional
    public ResponseEntity<ErrorResponse> editProfile(UserRequest userRequest) throws IOException {
        logger.info("editProfile: " + userRequest);
        User currentUser = userUtility.getCurrentUser();
        if (Objects.isNull(currentUser)) {
            throw new WrongDataException(UtilResponse.getErrorResponse());
        }
        currentUser.setName(userRequest.getName());
        String pathPhoto = userRequest.getPhoto() instanceof MultipartFile ?
                storageService.saveImage((MultipartFile) userRequest.getPhoto(), PHOTO_WEIGHT, PHOTO_HEIGHT) : null;
        if (Objects.nonNull(pathPhoto) || userRequest.isRemovePhoto()) {
            currentUser.setPhoto(pathPhoto);
        }
        if (Objects.nonNull(userRequest.getPassword())) {
            AppSecurityConfig appSecurityConfig = applicationContext.getBean(AppSecurityConfig.class);
            currentUser.setPassword(appSecurityConfig.passwordEncoder().encode(userRequest.getPassword()));
        }
        if (!currentUser.getEmail().equals(userRequest.getEmail())) {
            currentUser.setEmail(userRequest.getEmail());
        }
        userRepository.save(currentUser);
        logger.info("editProfile: save info user");
        return ResponseEntity.ok(new ErrorResponse());
    }
}
