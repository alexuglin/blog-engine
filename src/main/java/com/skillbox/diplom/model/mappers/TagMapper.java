package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.DTO.TagDTO;
import com.skillbox.diplom.model.Tag;
import com.skillbox.diplom.model.mappers.calculate.Calculator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(imports = Calculator.class)
public interface TagMapper {

    @Mapping(target = "weight", expression = "java(dWeightMax * Calculator.weightTag(countActivePosts, tag.getPostList().size()))")
    TagDTO convert(Tag tag, long countActivePosts, double dWeightMax);
}
