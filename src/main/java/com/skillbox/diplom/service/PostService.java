package com.skillbox.diplom.service;

import com.skillbox.diplom.exceptions.NotFoundPostException;
import com.skillbox.diplom.exceptions.enums.Errors;
import com.skillbox.diplom.model.DTO.PostDTO;
import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.User;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.model.api.response.PostsResponse;
import com.skillbox.diplom.model.enums.ModerationStatus;
import com.skillbox.diplom.model.mappers.PostMapper;
import com.skillbox.diplom.model.mappers.convert.DateConverter;
import com.skillbox.diplom.repository.PostRepository;
import com.skillbox.diplom.util.Paging;
import com.skillbox.diplom.util.UserUtility;
import com.skillbox.diplom.util.enums.FieldName;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserUtility userUtility;
    private final TagService tagService;
    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);
    private final Logger logger = Logger.getLogger(PostService.class);
    private static final String LOGGER_INFO = "%s: offset = %d limit = %d";

    public ResponseEntity<PostsResponse> getPost(int offset, int limit, String mode) {
        logger.info(String.format(LOGGER_INFO, "getPost: mode = " + mode, offset, limit));
        Page<Post> postPage;
        Pageable pageable = Paging.getPaging(offset, limit, Sort.unsorted());
        switch (mode) {
            case "popular":
                postPage = postRepository.findAllByPostsActiveOrderByCountComment(pageable);
                break;
            case "best":
                postPage = postRepository.findAllByPostsActiveOrderByCountVote(pageable);
                break;
            case "early":
                pageable = Paging.getPaging(offset, limit, Sort.by(FieldName.TIME.getDescription()).ascending());
                postPage = postRepository.findAllByPostsActive(pageable);
                break;
            default:
                pageable = Paging.getPaging(offset, limit, Sort.by(FieldName.TIME.getDescription()).descending());
                postPage = postRepository.findAllByPostsActive(pageable);
        }
        return ResponseEntity.ok(getPostsResponse(postPage));
    }

    public ResponseEntity<PostsResponse> searchPosts(int offset, int limit, String query) {
        logger.info(String.format(LOGGER_INFO, "searchPosts: query = " + query, offset, limit));
        Pageable pageable = Paging.getPaging(offset, limit, Sort.by(FieldName.TIME.getDescription()).descending());
        Page<Post> postPage = postRepository.findPosts(Objects.isNull(query) || query.isBlank() ? "" : query, pageable);
        return ResponseEntity.ok(getPostsResponse(postPage));
    }

    public ResponseEntity<PostsResponse> getPostsByDate(int offset, int limit, String date) {
        logger.info(String.format(LOGGER_INFO, "getPostsByDate: query = " + date, offset, limit));
        LocalDate localDate = DateConverter.stringToLocalDate(date);
        Page<Post> postPage = postRepository
                .findPostByDate(localDate, Paging.getPaging(offset, limit, Sort.by(FieldName.TIME.getDescription()).descending()));
        return ResponseEntity.ok(getPostsResponse(postPage));
    }

    public ResponseEntity<PostsResponse> getPostsByTag(int offset, int limit, String tag) {
        logger.info(String.format(LOGGER_INFO, "getPostsByTag: tag = " + tag, offset, limit));
        Page<Post> postPage = postRepository
                .findPostByTag(tag, Paging.getPaging(offset, limit, Sort.by(FieldName.TIME.getDescription()).descending()));
        return ResponseEntity.ok(getPostsResponse(postPage));
    }

    @Transactional
    public ResponseEntity<PostDTO> getPostById(Integer id) {
        logger.info("getPostById: came id - " + id);
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.orElse(new Post());
        User authorPost = post.getUser();
        User currentUser = userUtility.getCurrentUser();
        boolean visibility = post.isActive();
        if (!Objects.isNull(currentUser)) {
            visibility = authorPost.equals(currentUser) || currentUser.isModerator() || visibility;
        }
        if (!visibility && post.getModerationStatus() != ModerationStatus.ACCEPTED
                && post.getTime().compareTo(LocalDateTime.now()) > 0) {
            throw new NotFoundPostException(Errors.DOCUMENT_NOT_FOUND.getMessage());
        }
        if (Objects.isNull(currentUser) || (!authorPost.equals(currentUser) && !currentUser.isModerator())) {
            post.setViewCount(post.getViewCount() + 1);
        }
        postRepository.save(post);
        return ResponseEntity.ok(postMapper.convertTo(post));
    }

    public ResponseEntity<PostsResponse> getMyPosts(int offset, int limit, String status) {
        logger.info(String.format(LOGGER_INFO, "getMyPosts status = " + status, offset, limit));
        String email = UserUtility.getCurrentUserEmail();
        Pageable pageable = Paging.getPaging(offset, limit, Sort.by(FieldName.TIME.getDescription()).descending());
        Page<Post> postPage = status.equals("inactive") ? postRepository.findAllByPostNotActiveByUserEmail(email, pageable) :
                postRepository.findAllByPostByUserEmail(email, ModerationStatus.findModerationStatusByStatus(status), pageable);
        return ResponseEntity.ok(getPostsResponse(postPage));
    }

    public ResponseEntity<PostsResponse> getPostModeration(int offset, int limit, String status) {
        logger.info(String.format(LOGGER_INFO, "getPostModeration status = " + status, offset, limit));
        String email = UserUtility.getCurrentUserEmail();
        Pageable pageable = Paging.getPaging(offset, limit, Sort.by(FieldName.TIME.getDescription()).descending());
        ModerationStatus moderationStatus = ModerationStatus.findModerationStatusByStatus(status);
        Page<Post> postPage = moderationStatus == ModerationStatus.NEW ?
                postRepository.findAllByPostByModerationStatus(moderationStatus, pageable) :
                postRepository.findAllByPostByModeratorEmail(email, moderationStatus, pageable);
        return ResponseEntity.ok(getPostsResponse(postPage));
    }

    private PostsResponse getPostsResponse(Page<Post> postPage) {
        List<PostDTO> postDTOList = postMapper.pagePostToListPostDTO(postPage);
        return new PostsResponse(postPage.getTotalElements(), postDTOList);
    }

    @Transactional
    public ResponseEntity<ErrorResponse> addPost(PostDTO postDTO) {
        logger.info("addPost: " + postDTO);
        User user = userUtility.getCurrentUser();
        Post post = postMapper.postDTOToPost(postDTO, user);
        tagService.addTagsToPost(postDTO.getTagList(), post);
        postRepository.save(post);
        return ResponseEntity.ok(new ErrorResponse());
    }

    @Transactional
    public ResponseEntity<ErrorResponse> editPost(Integer id, PostDTO postDTO) {
        logger.info("editPost: " + postDTO + ", id = " + id);
        Post currentPost = postRepository.findById(id).orElse(new Post());
        currentPost.setActive(postDTO.getIsActive());
        currentPost.setTitle(postDTO.getTitle());
        currentPost.setText(postDTO.getText());
        User user = userUtility.getCurrentUser();
        if (!user.isModerator()) {
            currentPost.setModerationStatus(ModerationStatus.NEW);
        }
        DateConverter dateConverter = new DateConverter();
        currentPost.setTime(dateConverter.pastDateToCurrentDate(postDTO.getTimestamp()));
        tagService.editTagsFromPost(postDTO.getTagList(), currentPost);
        postRepository.save(currentPost);
        return ResponseEntity.ok(new ErrorResponse());
    }
}
