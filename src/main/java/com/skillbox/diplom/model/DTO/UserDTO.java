package com.skillbox.diplom.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Integer id;

    private String name;

    private String photo;

    private String email;

    @ToString.Exclude
    private String password;

    private Boolean moderation;

    private Integer moderationCount;

    private Boolean settings;
}
