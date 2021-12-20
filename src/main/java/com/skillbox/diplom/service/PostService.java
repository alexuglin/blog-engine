package com.skillbox.diplom.service;

import com.skillbox.diplom.exceptions.NotFoundPostException;
import com.skillbox.diplom.exceptions.enums.Errors;
import com.skillbox.diplom.model.DTO.PostDTO;
import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.User;
import com.skillbox.diplom.model.api.request.PostRequest;
import com.skillbox.diplom.model.api.request.PostsRequest;
import com.skillbox.diplom.model.api.request.RequestByDate;
import com.skillbox.diplom.model.api.request.SearchRequest;
import com.skillbox.diplom.model.api.request.TagRequest;
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
    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);
    private final Logger logger = Logger.getLogger(PostService.class);

    public ResponseEntity<PostsResponse> getPost(PostRequest postRequest) {
        logger.info("getPost: came - " + postRequest);
        Page<Post> postPage;
        Pageable pageable = Paging.getPaging(postRequest.getOffset(), postRequest.getLimit(), Sort.unsorted());
        switch (postRequest.getMode()) {
            case "popular":
                postPage = postRepository.findAllByPostsActiveOrderByCountComment(pageable);
                break;
            case "best":
                postPage = postRepository.findAllByPostsActiveOrderByCountVote(pageable);
                break;
            case "early":
                pageable = Paging.getPaging(postRequest.getOffset(), postRequest.getLimit(), Sort.by(FieldName.TIME.getDescription()).ascending());
                postPage = postRepository.findAllByPostsActive(pageable);
                break;
            default:
                pageable = Paging.getPaging(postRequest.getOffset(), postRequest.getLimit(), Sort.by(FieldName.TIME.getDescription()).descending());
                postPage = postRepository.findAllByPostsActive(pageable);
        }
        return ResponseEntity.ok(getPostsResponse(postPage));
    }

    public ResponseEntity<PostsResponse> searchPosts(SearchRequest searchRequest) {
        logger.info("searchPosts: came - " + searchRequest);
        Pageable pageable = Paging.getPaging(searchRequest.getOffset(), searchRequest.getLimit(), Sort.by(FieldName.TIME.getDescription()).descending());
        Page<Post> postPage = postRepository.findPosts(Objects.isNull(searchRequest.getQuery()) ||
                searchRequest.getQuery().isBlank() ? "" : searchRequest.getQuery(), pageable);
        return ResponseEntity.ok(getPostsResponse(postPage));
    }

    public ResponseEntity<PostsResponse> getPostsByDate(RequestByDate requestByDate) {
        logger.info("getPostsByDate: came - " + requestByDate);
        DateConverter dateConverter = new DateConverter();
        LocalDate localDate = dateConverter.stringToLocalDate(requestByDate.getDate());
        Page<Post> postPage = postRepository
                .findPostByDate(localDate, Paging.getPaging(requestByDate.getOffset(), requestByDate.getLimit(), Sort.by(FieldName.TIME.getDescription()).descending()));
        return ResponseEntity.ok(getPostsResponse(postPage));
    }

    public ResponseEntity<PostsResponse> getPostsByTag(TagRequest tagRequest) {
        logger.info("getPostsByTag: came - " + tagRequest);
        Page<Post> postPage = postRepository
                .findPostByTag(tagRequest.getTag(), Paging.getPaging(tagRequest.getOffset(), tagRequest.getLimit(), Sort.by(FieldName.TIME.getDescription()).descending()));
        return ResponseEntity.ok(getPostsResponse(postPage));
    }

    @Transactional
    public ResponseEntity<PostDTO> getPostById(Integer id) {
        logger.info("getPostById: came id - " + id);
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.orElse(new Post());
        String email = UserUtility.getCurrentUserEmail();
        boolean visibility = post.isActive();
        if (!Objects.isNull(email)) {
            User user = post.getUser();
            visibility = user.getEmail().equals(email) || user.isModerator() || visibility;
        }
        if (!visibility || post.getModerationStatus() != ModerationStatus.ACCEPTED
                || post.getTime().compareTo(LocalDateTime.now()) > 0) {
            throw new NotFoundPostException(Errors.DOCUMENT_NOT_FOUND.getMessage());
        }
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
        return ResponseEntity.ok(postMapper.convertTo(post));
    }

    public ResponseEntity<PostsResponse> getMyPosts(PostsRequest postsRequest) {
        logger.info("getMyPosts" + postsRequest);
        String email = UserUtility.getCurrentUserEmail();
        Pageable pageable = Paging.getPaging(postsRequest.getOffset(), postsRequest.getLimit(), Sort.by(FieldName.TIME.getDescription()).descending());
        String status = postsRequest.getStatus();
        Page<Post> postPage = status.equals("inactive") ? postRepository.findAllByPostNotActiveByUserEmail(email, pageable) :
                postRepository.findAllByPostByUserEmail(email, ModerationStatus.findModerationStatusByStatus(status), pageable);
        return ResponseEntity.ok(getPostsResponse(postPage));
    }

    public ResponseEntity<PostsResponse> getPostModeration(PostsRequest postsRequest) {
        logger.info("getPostModeration" + postsRequest);
        String email = UserUtility.getCurrentUserEmail();
        Pageable pageable = Paging.getPaging(postsRequest.getOffset(), postsRequest.getLimit(), Sort.by(FieldName.TIME.getDescription()).descending());
        String status = postsRequest.getStatus();
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
}
