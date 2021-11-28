package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.TagToPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TagToPostRepository extends JpaRepository<TagToPost, Integer> {

    @Query(value = "SELECT COUNT(p.id) " +
            "FROM tag2post tp " +
            "      INNER JOIN posts p ON tp.post_id = p.id " +
            "WHERE p.moderation_status = 'ACCEPTED' " +
            "       AND p.is_active = 1 " +
            "       AND p.time <= CURRENT_TIMESTAMP " +
            "GROUP BY tp.tag_id " +
            "ORDER BY count(p.id) DESC " +
            "LIMIT 1", nativeQuery = true)
    int getCountPostsFromTags();

    @Query("SELECT tp " +
            "FROM TagToPost tp " +
            "WHERE tp.post in " +
            "(SELECT post " +
            "   FROM Post post " +
            "   WHERE post.isActive = true " +
            "       and post.moderationStatus = 'ACCEPTED' " +
            "       and post.time <= current_timestamp)" +
            "and tp.tag.name like :nameTag%")
    List<TagToPost> findTagToPostByActive(@Param("nameTag") String nameTag);
}
