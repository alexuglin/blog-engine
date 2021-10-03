package com.skillbox.diplom.controller;

import com.skillbox.diplom.model.api.response.InitResponse;
import com.skillbox.diplom.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final GeneralService generalService;

    @Autowired
    public ApiGeneralController(GeneralService generalService) {
        this.generalService = generalService;
    }

    @GetMapping("/init")
    public ResponseEntity<InitResponse> init() {
        return generalService.getInfo();
    }

}