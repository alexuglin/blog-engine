package com.skillbox.diplom.controller;

import com.skillbox.diplom.model.api.response.PostResponse;
import com.skillbox.diplom.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    private final PostService postService;

    @Autowired
    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<PostResponse> getPosts(@RequestParam(required = false) int offset,
                                                 @RequestParam(required = false) int limit,
                                                 @RequestParam(required = false) String mode) {
        return postService.getPost(offset, limit, mode);
    }
}