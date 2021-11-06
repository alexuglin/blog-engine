package com.skillbox.diplom.model.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skillbox.diplom.model.DTO.PostDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponse {

    private Long count;

    private List<PostDTO> posts;
}
