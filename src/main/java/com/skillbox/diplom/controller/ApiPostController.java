package com.skillbox.diplom.controller;

import com.skillbox.diplom.model.DTO.PostDTO;
import com.skillbox.diplom.model.api.request.PostRequest;
import com.skillbox.diplom.model.api.request.PostsRequest;
import com.skillbox.diplom.model.api.request.RequestByDate;
import com.skillbox.diplom.model.api.request.SearchRequest;
import com.skillbox.diplom.model.api.request.TagRequest;
import com.skillbox.diplom.model.api.response.PostsResponse;
import com.skillbox.diplom.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<PostsResponse> getPosts(PostRequest postRequest) {
        return postService.getPost(postRequest);
    }

    @GetMapping("/search")
    public ResponseEntity<PostsResponse> searchPosts(SearchRequest searchRequest) {
        return postService.searchPosts(searchRequest);
    }

    @GetMapping("/byDate")
    public ResponseEntity<PostsResponse> getPostsByDate(RequestByDate requestByDate) {
        return postService.getPostsByDate(requestByDate);
    }

    @GetMapping("/byTag")
    public ResponseEntity<PostsResponse> getPostsByTag(TagRequest tagRequest) {
        return postService.getPostsByTag(tagRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer id) {
        return postService.getPostById(id);
    }

    @GetMapping("/my")
    public ResponseEntity<PostsResponse> getMyPosts(PostsRequest postsRequest) {
        return postService.getMyPosts(postsRequest);
    }
}