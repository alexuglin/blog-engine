package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.DTO.StatisticsPost;
import com.skillbox.diplom.model.api.response.StatisticsBlog;
import com.skillbox.diplom.model.mappers.convert.DateConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = DateConverter.class)
public interface StatisticsMapper {

    @Mapping(target = "postsCount", source = "statisticsPost.postsCount")
    @Mapping(target = "likesCount", source = "likesCount")
    @Mapping(target = "dislikesCount", source = "dislikesCount")
    @Mapping(target = "viewsCount", source = "statisticsPost.viewsCount")
    @Mapping(target = "firstPublication", source = "statisticsPost.firstPublication", qualifiedByName = "dateToLong")
    StatisticsBlog convertTo(StatisticsPost statisticsPost, long likesCount, long dislikesCount);
}
