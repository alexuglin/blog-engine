package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("SELECT tag " +
            "FROM Tag tag " +
            "WHERE tag.name IN :tagList")
    List<Tag> findTags(@Param("tagList") List<String> tagList);

    @Modifying
    @Query("SELECT tag " +
            "FROM Tag tag " +
            "LEFT JOIN TagToPost t2p " +
            "ON tag = t2p.tag " +
            "WHERE t2p.post IS NULL")
    List<Tag> selectUnusedTags();
}
