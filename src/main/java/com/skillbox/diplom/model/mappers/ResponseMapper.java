package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.DTO.UserDTO;
import com.skillbox.diplom.model.api.response.AuthResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = UserMapper.class)
public interface ResponseMapper {

    @Mapping(target = "result", source = "result")
    @Mapping(target = "user", source = "userDTO")
    AuthResponse convertTo(UserDTO userDTO, boolean result);
}
