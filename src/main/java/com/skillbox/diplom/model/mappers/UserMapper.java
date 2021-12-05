package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.DTO.UserDTO;
import com.skillbox.diplom.model.User;
import com.skillbox.diplom.model.api.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(imports = LocalDateTime.class)
public interface UserMapper {

    UserDTO convertTo(User user);

    @Mapping(target = "regTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "moderator", expression = "java(false)")
    User userRequestToUser(UserRequest userRequest);
}
