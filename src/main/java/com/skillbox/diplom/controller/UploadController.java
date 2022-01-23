package com.skillbox.diplom.controller;

import com.skillbox.diplom.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class UploadController {

    private final StorageService imageService;

    @GetMapping("/**")
    public ResponseEntity<byte[]> getImage(HttpServletRequest request) throws IOException {
        return imageService.getImage(request);
    }
}
