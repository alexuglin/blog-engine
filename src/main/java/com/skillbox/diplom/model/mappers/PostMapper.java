package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.DTO.PostDTO;
import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.mappers.convert.DateConverter;
import com.skillbox.diplom.model.mappers.convert.TextConverter;
import com.skillbox.diplom.model.mappers.calculate.Counter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateConverter.class, UserMapper.class, TextConverter.class, Counter.class})
public interface PostMapper {

    @Mapping(target = "timestamp", source = "post.time", qualifiedByName = "dateToLong")
    @Mapping(target = "announce", source = "post.text", qualifiedByName = "textToAnnounce")
    @Mapping(target = "commentCount", source = "post.postComments", qualifiedByName = "calculateCountElementsList")
    @Mapping(target = "likeCount", expression = "java(Counter.calculateVote(post.getPostVoteList(), 1))")
    @Mapping(target = "dislikeCount", expression = "java(Counter.calculateVote(post.getPostVoteList(), -1))")
    PostDTO convertTo(Post post);
}
