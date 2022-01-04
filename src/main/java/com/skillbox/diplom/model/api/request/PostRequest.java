package com.skillbox.diplom.model.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostRequest {

    @JsonProperty(value = "post_id")
    private Integer postId;

    private String decision;
}
