package com.skillbox.diplom.controller;

import com.skillbox.diplom.model.DTO.CommentDTO;
import com.skillbox.diplom.model.DTO.TagDTO;
import com.skillbox.diplom.model.api.request.PostRequest;
import com.skillbox.diplom.model.api.request.UserRequest;
import com.skillbox.diplom.model.api.response.CalendarResponse;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.model.api.response.InitResponse;
import com.skillbox.diplom.model.enums.NameSetting;
import com.skillbox.diplom.model.validation.OnEditProfile;
import com.skillbox.diplom.model.validation.annotation.ConstraintImage;
import com.skillbox.diplom.service.InfoService;
import com.skillbox.diplom.service.ModerationService;
import com.skillbox.diplom.service.PostCommentService;
import com.skillbox.diplom.service.ProfileService;
import com.skillbox.diplom.service.StorageService;
import com.skillbox.diplom.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiGeneralController {

    private final InfoService infoService;
    private final TagService tagService;
    private final StorageService storageService;
    private final PostCommentService postCommentService;
    private final ModerationService moderationService;
    private final ProfileService profileService;


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

    @PreAuthorize("hasAnyAuthority('user:write')")
    @PostMapping("/image")
    @ResponseBody
    public String loadImage(@ConstraintImage @RequestParam("image") MultipartFile image) throws IOException {
        return storageService.loadImage(image);
    }

    @PreAuthorize("hasAnyAuthority('user:write')")
    @PostMapping("/comment")
    public ResponseEntity<Map<String, Integer>> getIdComment(@Valid @RequestBody CommentDTO commentDTO) {
        return postCommentService.getIdComment(commentDTO);
    }

    @PreAuthorize("hasAnyAuthority('user:moderate')")
    @PostMapping("/moderation")
    public ResponseEntity<ErrorResponse> moderationPost(@RequestBody PostRequest moderationRequest) {
        return moderationService.moderationPost(moderationRequest);
    }

    @PreAuthorize("hasAnyAuthority('user:write')")
    @PostMapping(value = "/profile/my", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Validated(OnEditProfile.class)
    public ResponseEntity<ErrorResponse> editProfileWithoutPhoto(@Valid @RequestBody UserRequest userRequest) throws IOException {
        return profileService.editProfile(userRequest);
    }

    @PreAuthorize("hasAnyAuthority('user:write')")
    @PostMapping(value = "/profile/my", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Validated(OnEditProfile.class)
    public ResponseEntity<ErrorResponse> editProfileWithPhoto(@Valid UserRequest userRequest) throws IOException {
        return profileService.editProfile(userRequest);
    }
}