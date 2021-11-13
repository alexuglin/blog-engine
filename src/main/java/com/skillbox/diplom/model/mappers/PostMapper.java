package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.DTO.PostDTO;
import com.skillbox.diplom.model.Post;
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
        CommentMapper.class, TagMapper.class})
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

    @Mapping(target = "announce", ignore = true)
    @Mapping(target = "timestamp", source = "post.time", qualifiedByName = "dateToLong")
    @Mapping(target = "commentCount", ignore = true)
    @Mapping(target = "likeCount", source = "post.postVoteList", qualifiedByName = "calculateCountLikes")
    @Mapping(target = "dislikeCount", source = "post.postVoteList", qualifiedByName = "calculateCountDislikes")
    PostDTO convertTo(Post post);

    @IterableMapping(qualifiedByName = "convertAnnounceTo")
    List<PostDTO> pagePostToListPostDTO(Page<Post> postPage);
}
