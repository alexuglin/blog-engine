package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.PostVote;
import com.skillbox.diplom.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(imports = LocalDateTime.class)
public interface PostVoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "time", expression = "java(LocalDateTime.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "user", source = "currentUser")
    PostVote getPostVote(Post post, User currentUser, byte value);
}
