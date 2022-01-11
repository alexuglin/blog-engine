package com.skillbox.diplom.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {

    private Integer id;

    private Long timestamp;

    @JsonProperty(value = "active")
    private Boolean isActive;

    private UserDTO user;

    @Size(min = 3, message = "Заголовок не установлен или слишком короткий")
    private String title;

    private String announce;

    @Size(min = 50, message = "Текст публикации не задан или слишком короткий")
    private String text;

    private Integer likeCount;

    private Integer dislikeCount;

    private Integer commentCount;

    private Integer viewCount;

    @JsonProperty(value = "comments")
    private List<CommentDTO> postComments;

    @JsonProperty(value = "tags")
    private List<String> tagList;
}
