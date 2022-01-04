package com.skillbox.diplom.controller;

import com.skillbox.diplom.model.DTO.PostDTO;
import com.skillbox.diplom.model.api.request.PostRequest;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.model.api.response.PostsResponse;
import com.skillbox.diplom.service.PostService;
import com.skillbox.diplom.service.PostVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    private final PostService postService;
    private final PostVoteService postVoteService;

    @Autowired
    public ApiPostController(PostService postService, PostVoteService postVoteService) {
        this.postService = postService;
        this.postVoteService = postVoteService;
    }

    @GetMapping
    public ResponseEntity<PostsResponse> getPosts(@RequestParam int offset,
                                                  @RequestParam int limit,
                                                  @RequestParam(required = false) String mode) {
        return postService.getPost(offset, limit, mode);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<ErrorResponse> addPost(@Valid @RequestBody PostDTO postDTO) {

        return postService.addPost(postDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<ErrorResponse> editPost(@PathVariable Integer id, @Valid @RequestBody PostDTO postDTO) {

        return postService.editPost(id, postDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<PostsResponse> searchPosts(@RequestParam int offset,
                                                     @RequestParam int limit,
                                                     @RequestParam(required = false) String query) {
        return postService.searchPosts(offset, limit, query);
    }

    @GetMapping("/byDate")
    public ResponseEntity<PostsResponse> getPostsByDate(@RequestParam int offset,
                                                        @RequestParam int limit,
                                                        @RequestParam(required = false) String date) {
        return postService.getPostsByDate(offset, limit, date);
    }

    @GetMapping("/byTag")
    public ResponseEntity<PostsResponse> getPostsByTag(@RequestParam int offset,
                                                       @RequestParam int limit,
                                                       @RequestParam(required = false) String tag) {
        return postService.getPostsByTag(offset, limit, tag);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer id) {
        return postService.getPostById(id);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<PostsResponse> getMyPosts(@RequestParam int offset,
                                                    @RequestParam int limit,
                                                    @RequestParam(required = false) String status) {
        return postService.getMyPosts(offset, limit, status);
    }

    @GetMapping("/moderation")
    @PreAuthorize("hasAnyAuthority('user:moderate')")
    public ResponseEntity<PostsResponse> getPostModeration(@RequestParam int offset,
                                                           @RequestParam int limit,
                                                           @RequestParam(required = false) String status) {
        return postService.getPostModeration(offset, limit, status);
    }

    @PostMapping("/like")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<ErrorResponse> setLike(@RequestBody PostRequest postRequest) {
        return postVoteService.setLike(postRequest);
    }

    @PostMapping("/dislike")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<ErrorResponse> setDislike(@RequestBody PostRequest postRequest) {
        return postVoteService.setDislike(postRequest);
    }
}