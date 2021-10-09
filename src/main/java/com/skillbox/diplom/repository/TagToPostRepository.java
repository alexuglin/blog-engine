package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.TagToPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TagToPostRepository extends JpaRepository<TagToPost, Integer> {

    @Query("SELECT DISTINCT count (tp.post) FROM TagToPost tp WHERE tp.post in " +
            "(SELECT post FROM Post post WHERE post.isActive = true and post.moderationStatus = 'ACCEPTED' and post.time <= current_timestamp)" +
            "GROUP BY tp.tag ORDER BY 1 DESC")
    List<Integer> getCountPostsFromTags();

    @Query("SELECT tp FROM TagToPost tp WHERE tp.post in " +
            "(SELECT post FROM Post post WHERE post.isActive = true and post.moderationStatus = 'ACCEPTED' and post.time <= current_timestamp)" +
            "and tp.tag.name like :nameTag%")
    List<TagToPost> findTagToPostByActive(@Param("nameTag") String nameTag);
}
