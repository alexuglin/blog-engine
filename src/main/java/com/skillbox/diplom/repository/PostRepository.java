package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    @Query(value = "SELECT  post  FROM Post  post " +
            "WHERE post.isActive = true and post.time <= current_timestamp and post.moderationStatus = 'ACCEPTED'")
    Page<Post> findAllByPostsActive(Pageable page);

    @Query(value = "SELECT  post  FROM Post  post " +
            "WHERE post.isActive = true and post.time <= current_timestamp and post.moderationStatus = 'ACCEPTED'" +
            "ORDER BY function ('SIZE', post.postComments) DESC ")
    Page<Post> findAllByPostsActiveOrderByCountComment(Pageable page);

    @Query(value = "SELECT  post  FROM Post  post " +
            "WHERE post.isActive = true and post.time <= current_timestamp and post.moderationStatus = 'ACCEPTED'" +
            "ORDER BY function ('SIZE', post.postVoteList) DESC ")
    Page<Post> findAllByPostsActiveOrderByCountVote(Pageable page);

    @Query(value = "SELECT COUNT(post) FROM Post post " +
            "WHERE post.isActive = true and post.time <= current_timestamp and post.moderationStatus = 'ACCEPTED'")
    long countAllActivePosts();
}
