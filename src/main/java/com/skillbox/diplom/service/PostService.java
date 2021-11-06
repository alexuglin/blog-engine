package com.skillbox.diplom.service;

import com.skillbox.diplom.model.DTO.PostDTO;
import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.api.response.PostResponse;
import com.skillbox.diplom.model.mappers.PostMapper;
import com.skillbox.diplom.repository.PostRepository;
import com.skillbox.diplom.util.Paging;
import com.skillbox.diplom.util.enums.FieldName;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ResponseEntity<PostResponse> getPost(int offset, int limit, String mode) {
        Page<Post> pageList;
        Pageable pageable = Paging.getPaging(offset, limit, Sort.unsorted());
        switch (mode) {
            case "popular":
                pageList = postRepository.findAllByPostsActiveOrderByCountComment(pageable);
                break;
            case "best":
                pageList = postRepository.findAllByPostsActiveOrderByCountVote(pageable);
                break;
            case "early":
                pageable = Paging.getPaging(offset, limit, Sort.by(FieldName.TIME.getDescription()).ascending());
                pageList = postRepository.findAllByPostsActive(pageable);
                break;
            default:
                pageable = Paging.getPaging(offset, limit, Sort.by(FieldName.TIME.getDescription()).descending());
                pageList = postRepository.findAllByPostsActive(pageable);
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setCount(pageList.getTotalElements());
        List<PostDTO> postDTOList = pageList.stream()
                .map(postMapper::convertTo)
                .collect(Collectors.toList());
        postResponse.setPosts(postDTOList);
        return ResponseEntity.ok(postResponse);
    }
}
