package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.DTO.UserDTO;
import com.skillbox.diplom.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDTO convertTo(User user);
}
