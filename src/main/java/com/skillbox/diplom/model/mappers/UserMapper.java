package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.DTO.UserDTO;
import com.skillbox.diplom.model.User;
import com.skillbox.diplom.model.api.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(imports = LocalDateTime.class)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "moderation", source = "user.moderator")
    @Mapping(target = "settings", source = "user.moderator")
    @Mapping(target = "moderationCount", source = "moderationCount")
    UserDTO convertTo(User user, int moderationCount);

    @Mapping(target = "regTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "moderator", expression = "java(false)")
    User userRequestToUser(UserRequest userRequest);
}
