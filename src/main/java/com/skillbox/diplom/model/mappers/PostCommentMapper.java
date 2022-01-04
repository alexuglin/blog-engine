package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.DTO.CommentDTO;
import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.PostComment;
import com.skillbox.diplom.model.User;
import com.skillbox.diplom.model.mappers.convert.DateConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(uses = {UserMapper.class, DateConverter.class}, imports = LocalDateTime.class)
public interface PostCommentMapper {

    @Mapping(target = "timestamp", source = "comment.time", qualifiedByName = "dateToLong")
    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "parentId", ignore = true)
    CommentDTO convertTo(PostComment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "time", expression = "java(LocalDateTime.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "text", source = "commentDTO.text")
    @Mapping(target = "parentComment", source = "parentComment")
    @Mapping(target = "user", source = "currentUser")
    @Mapping(target = "comments", ignore = true)
    PostComment getPostComment(CommentDTO commentDTO, Post post, PostComment parentComment, User currentUser);

}
