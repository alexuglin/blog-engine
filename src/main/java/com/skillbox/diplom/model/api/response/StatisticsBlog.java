package com.skillbox.diplom.model.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsBlog {

    private Long postsCount;

    private Long likesCount;

    private Long dislikesCount;

    private Long viewsCount;

    private Long firstPublication;
}
