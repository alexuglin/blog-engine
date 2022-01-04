package com.skillbox.diplom.service;

import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.PostVote;
import com.skillbox.diplom.model.User;
import com.skillbox.diplom.model.api.request.PostRequest;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.model.mappers.PostVoteMapper;
import com.skillbox.diplom.repository.PostRepository;
import com.skillbox.diplom.repository.PostVoteRepository;
import com.skillbox.diplom.util.UserUtility;
import com.skillbox.diplom.util.UtilResponse;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostVoteService {

    private final PostRepository postRepository;
    private final PostVoteRepository postVoteRepository;
    private final UserUtility userUtility;
    private final PostVoteMapper postVoteMapper = Mappers.getMapper(PostVoteMapper.class);

    @Transactional
    public ResponseEntity<ErrorResponse> setLike(PostRequest postRequest) {
        return ResponseEntity.ok(getErrorResponseFromLikeOrDislike(postRequest, (byte) 1));
    }

    @Transactional
    public ResponseEntity<ErrorResponse> setDislike(PostRequest postRequest) {
        return ResponseEntity.ok(getErrorResponseFromLikeOrDislike(postRequest, (byte) -1));
    }

    private ErrorResponse getErrorResponseFromLikeOrDislike(PostRequest postRequest, byte value) {
        Post post = postRepository.findById(postRequest.getPostId()).orElse(null);
        if (Objects.isNull(post)) {
            return UtilResponse.getErrorResponse();
        }
        User currentUser = userUtility.getCurrentUser();
        Optional<PostVote> postVoteOptional = postVoteRepository.findPostVoteByPostAndUser(post, currentUser);
        PostVote postVote = postVoteOptional.orElseGet(() -> postVoteMapper.getPostVote(post, currentUser, value));
        if (!Objects.isNull(postVote.getId()) && postVote.getValue() == value) {
            return UtilResponse.getErrorResponse();
        }
        postVote.setValue(value);
        postVoteRepository.save(postVote);
        return new ErrorResponse();
    }
}
