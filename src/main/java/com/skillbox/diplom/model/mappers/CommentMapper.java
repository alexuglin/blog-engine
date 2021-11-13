package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.DTO.CommentDTO;
import com.skillbox.diplom.model.PostComment;
import com.skillbox.diplom.model.mappers.convert.DateConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {UserMapper.class, DateConverter.class})
public interface CommentMapper {

    @Mapping(target = "timestamp", source = "comment.time", qualifiedByName = "dateToLong")
    CommentDTO convertTo(PostComment comment);
}
