package com.skillbox.diplom.model.mappers;

import com.skillbox.diplom.model.DTO.TagDTO;
import com.skillbox.diplom.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class TagMapper {

    @Mapping(target = "weight", source = "weight")
    public abstract TagDTO convertTo(Tag tag, double weight);

    public String tagToString(Tag tag) {
        return tag.getName();
    }
}
