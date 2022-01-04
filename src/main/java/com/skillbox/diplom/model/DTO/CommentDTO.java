package com.skillbox.diplom.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {

    private Integer id;

    private Long timestamp;

    @Size(min = 3)
    @NotBlank
    private String text;

    private UserDTO user;

    @JsonProperty(value = "parent_id")
    private Integer parentId;

    @NotNull
    @JsonProperty(value = "post_id")
    private Integer postId;

    private List<CommentDTO> comments;
}
