package com.skillbox.diplom.service;

import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.api.request.PostRequest;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.model.enums.ModerationStatus;
import com.skillbox.diplom.repository.PostRepository;
import com.skillbox.diplom.util.UserUtility;
import com.skillbox.diplom.util.UtilResponse;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModerationService {

    private final Logger logger = Logger.getLogger(ModerationService.class);
    private final PostRepository postRepository;
    private final UserUtility userUtility;

    @Transactional
    public ResponseEntity<ErrorResponse> moderationPost(PostRequest postRequest) {
        logger.info("moderationPost: " + postRequest);
        Optional<Post> postOptional = postRepository.findById(postRequest.getPostId());
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setModerationStatus(ModerationStatus.findModerationStatusByStatus(postRequest.getDecision()));
            post.setModerator(userUtility.getCurrentUser());
            postRepository.save(post);
            return ResponseEntity.ok(new ErrorResponse());
        }
        return ResponseEntity.ok(UtilResponse.getErrorResponse());
    }
}
