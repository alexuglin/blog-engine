package com.skillbox.diplom.controller;

import com.skillbox.diplom.model.DTO.TagDTO;
import com.skillbox.diplom.model.api.response.CalendarResponse;
import com.skillbox.diplom.model.api.response.InitResponse;
import com.skillbox.diplom.model.enums.NameSetting;
import com.skillbox.diplom.service.InfoService;
import com.skillbox.diplom.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final InfoService infoService;
    private final TagService tagService;

    @Autowired
    public ApiGeneralController(InfoService infoService, TagService tagService) {
        this.infoService = infoService;
        this.tagService = tagService;
    }

    @GetMapping("/init")
    public ResponseEntity<InitResponse> init() {
        return infoService.getInfo();
    }

    @GetMapping("/settings")
    public ResponseEntity<EnumMap<NameSetting, Boolean>> getSettings() {
        return infoService.getSettings();
    }

    @GetMapping("/tag")
    public ResponseEntity<Map<String, List<TagDTO>>> getTags(@RequestParam(required = false) String query) {
        return tagService.getTags(query);
    }

    @GetMapping("/calendar")
    public ResponseEntity<CalendarResponse> getCountPostsInDay(Integer year) {
        return infoService.getCountPostsInDay(year);
    }
}