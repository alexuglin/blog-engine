package com.skillbox.diplom.service;

import com.skillbox.diplom.model.DTO.TagDTO;
import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.Tag;
import com.skillbox.diplom.model.TagToPost;
import com.skillbox.diplom.model.enums.ModerationStatus;
import com.skillbox.diplom.model.mappers.TagMapper;
import com.skillbox.diplom.model.mappers.calculate.Calculator;
import com.skillbox.diplom.repository.PostRepository;
import com.skillbox.diplom.repository.TagRepository;
import com.skillbox.diplom.repository.TagToPostRepository;
import com.skillbox.diplom.util.enums.FieldName;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final PostRepository postRepository;
    private final TagToPostRepository tagToPostRepository;
    private final TagRepository tagRepository;
    private final TagMapper tagMapper = Mappers.getMapper(TagMapper.class);
    private final Logger logger = Logger.getLogger(TagService.class);

    public ResponseEntity<Map<String, List<TagDTO>>> getTags(String query) {
        long countAllPosts = postRepository
                .countPostByModerationStatusAndIsActiveAndTimeLessThanEqual(ModerationStatus.ACCEPTED, true, LocalDateTime.now());
        List<TagToPost> tagToPostList = tagToPostRepository.findTagToPostByActive(Objects.isNull(query) ? "" : query);
        int maxCountPostsFromTags = tagToPostRepository.getCountPostsFromTags();
        double weightMax = 1 / Calculator.weightTag(countAllPosts, maxCountPostsFromTags);
        List<TagDTO> tagDTOList = tagToPostList
                .stream()
                .map(TagToPost::getTag)
                .distinct()
                .map(tag -> {
                    double weight = weightMax * Calculator.weightTag(countAllPosts, Calculator.countPostsCurrentTag(tag));
                    return tagMapper.tagToTagDTO(tag, weight);
                }).collect(Collectors.toList());
        Map<String, List<TagDTO>> tags = new HashMap<>();
        tags.put(FieldName.TAGS.getDescription(), tagDTOList);
        return ResponseEntity.ok(tags);
    }

    public void addTagsToPost(List<String> tagList, Post newPost) {
        String nameMethod = "addTagsToPost: ";
        logger.info(nameMethod + tagList);
        newPost.setTagList(getListTag(tagList));
        logger.info(nameMethod + newPost);
    }

    @Transactional
    public void editTagsFromPost(List<String> newTagList, Post post) {
        String nameMethod = "editTagsFromPost: ";
        logger.info(nameMethod + newTagList);
        List<Tag> newTags = getListTag(newTagList);
        List<Tag> tagsDeleting = post
                .getTagList()
                .stream()
                .filter(tag -> !newTags.contains(tag))
                .collect(Collectors.toList());
        tagToPostRepository.deleteTags(tagsDeleting, post.getId());
        logger.info(nameMethod.concat("delete: " + tagsDeleting));
        post.setTagList(newTags);
        logger.info(nameMethod + post);
    }

    private List<Tag> getListTag(List<String> tagList) {
        List<Tag> foundTags = tagRepository.findTags(tagList);
        List<Tag> newTags = tagList.stream()
                .map(nameTag -> {
                    Tag tag = new Tag();
                    tag.setName(nameTag.toLowerCase());
                    return tag;
                }).filter(tag -> !foundTags.contains(tag))
                .collect(Collectors.toList());;
        newTags.addAll(foundTags);
        return newTags;
    }
}
