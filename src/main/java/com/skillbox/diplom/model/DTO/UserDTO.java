package com.skillbox.diplom.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    private String name;

    private String photo;

    private String email;

    private boolean moderation;

    private int moderationCount;

    private boolean settings = true;
}
