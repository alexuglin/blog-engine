package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.DTO.PostDTO;
import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.User;
import com.skillbox.diplom.model.enums.ModerationStatus;
import com.skillbox.diplom.model.mappers.calculate.CounterVotes;
import com.skillbox.diplom.model.mappers.convert.DateConverter;
import com.skillbox.diplom.model.mappers.convert.TextConverter;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(uses = {DateConverter.class, UserMapper.class, TextConverter.class, CounterVotes.class,
        PostCommentMapper.class, TagMapper.class},
        imports = ModerationStatus.class)
public interface PostMapper {

    @Named(value = "convertAnnounceTo")
    @Mapping(target = "timestamp", source = "post.time", qualifiedByName = "dateToLong")
    @Mapping(target = "announce", source = "post.text", qualifiedByName = "textToAnnounce")
    @Mapping(target = "commentCount", source = "post.postComments", qualifiedByName = "calculateCountElementsList")
    @Mapping(target = "likeCount", source = "post.postVoteList", qualifiedByName = "calculateCountLikes")
    @Mapping(target = "dislikeCount", source = "post.postVoteList", qualifiedByName = "calculateCountDislikes")
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "postComments", ignore = true)
    @Mapping(target = "tagList", ignore = true)
    PostDTO convertAnnounceTo(Post post);

    @Mapping(target = "isActive", source = "post.active")
    @Mapping(target = "announce", ignore = true)
    @Mapping(target = "timestamp", source = "post.time", qualifiedByName = "dateToLong")
    @Mapping(target = "commentCount", ignore = true)
    @Mapping(target = "likeCount", source = "post.postVoteList", qualifiedByName = "calculateCountLikes")
    @Mapping(target = "dislikeCount", source = "post.postVoteList", qualifiedByName = "calculateCountDislikes")
    PostDTO convertTo(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", source = "postDTO.isActive")
    @Mapping(target = "time", source = "postDTO.timestamp", qualifiedByName = "pastDateToCurrentDate")
    @Mapping(target = "moderationStatus", source = "moderationStatus")
    @Mapping(target = "user", source = "currentUser")
    @Mapping(target = "viewCount", expression = "java(0)")
    @Mapping(target = "tagList", ignore = true)
    @Mapping(target = "moderator", ignore = true)
    Post postDTOToPost(PostDTO postDTO, User currentUser, ModerationStatus moderationStatus);

    @IterableMapping(qualifiedByName = "convertAnnounceTo")
    List<PostDTO> pagePostToListPostDTO(Page<Post> postPage);
}
