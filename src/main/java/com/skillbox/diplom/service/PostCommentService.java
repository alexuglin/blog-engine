package com.skillbox.diplom.service;

import com.skillbox.diplom.exceptions.WrongDataException;
import com.skillbox.diplom.exceptions.enums.Errors;
import com.skillbox.diplom.model.DTO.CommentDTO;
import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.PostComment;
import com.skillbox.diplom.model.User;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.model.mappers.PostCommentMapper;
import com.skillbox.diplom.repository.PostCommentRepository;
import com.skillbox.diplom.repository.PostRepository;
import com.skillbox.diplom.util.UserUtility;
import com.skillbox.diplom.util.UtilResponse;
import com.skillbox.diplom.util.enums.FieldName;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserUtility userUtility;
    private final PostCommentMapper postCommentMapper = Mappers.getMapper(PostCommentMapper.class);
    private final Logger logger = Logger.getLogger(PostCommentService.class);

    @Transactional
    public ResponseEntity<Map<String, Integer>> getIdComment(CommentDTO commentDTO) {
        logger.info("getIdComment: " + commentDTO);
        boolean isNullParentId = Objects.isNull(commentDTO.getParentId());
        Map<String, Integer> responseMap = new HashMap<>();
        Optional<Post> optionalPost = postRepository.findById(commentDTO.getPostId());
        Optional<PostComment> optionalParentComment = !isNullParentId ?
                postCommentRepository.findById(commentDTO.getParentId()) : Optional.empty();
        if (optionalPost.isEmpty() || (!isNullParentId && optionalParentComment.isEmpty())) {
            ErrorResponse errorResponse = UtilResponse.getErrorResponse();
            errorResponse.setErrors(Map.of(Errors.DOCUMENT_NOT_FOUND.getFieldName(), Errors.DOCUMENT_NOT_FOUND.getMessage()));
            throw new WrongDataException(errorResponse);
        }
        User currentUser = userUtility.getCurrentUser();
        Post post = optionalPost.get();
        PostComment parentComment = optionalParentComment.orElse(null);
        PostComment postComment = postCommentMapper.getPostComment(commentDTO, post, parentComment, currentUser);
        Integer commentId = postCommentRepository.save(postComment).getId();
        responseMap.put(FieldName.ID.getDescription(), commentId);
        logger.info("getIdComment: id = " + commentId);
        return ResponseEntity.ok(responseMap);
    }
}
