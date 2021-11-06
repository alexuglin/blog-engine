package com.skillbox.diplom.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {

    private Integer id;

    private Long timestamp;

    private UserDTO user;

    private String title;

    private String announce;

    private Integer likeCount;

    private Integer dislikeCount;

    private Integer commentCount;

    private Integer viewCount;
}
