package com.skillbox.diplom.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Integer id;

    private String name;

    private String photo;

    private String email;

    private boolean moderation;

    private int moderationCount;

    private boolean settings = true;
}
