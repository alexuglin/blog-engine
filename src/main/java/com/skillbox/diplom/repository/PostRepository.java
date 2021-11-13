package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.DTO.CountPostsDay;
import com.skillbox.diplom.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT  post  " +
            "FROM Post  post " +
            "WHERE post.isActive = true" +
            "   AND post.time <= CURRENT_TIMESTAMP" +
            "   AND post.moderationStatus = 'ACCEPTED'")
    Page<Post> findAllByPostsActive(Pageable page);

    @Query(value = "SELECT  post  " +
            "FROM Post  post " +
            "WHERE post.isActive = true" +
            "   AND post.time <= CURRENT_TIMESTAMP" +
            "   AND post.moderationStatus = 'ACCEPTED'" +
            "ORDER BY function ('SIZE', post.postComments) DESC")
    Page<Post> findAllByPostsActiveOrderByCountComment(Pageable page);

    @Query(value = "SELECT  post  " +
            "FROM Post  post " +
            "WHERE post.isActive = true" +
            "   AND post.time <= CURRENT_TIMESTAMP" +
            "   AND post.moderationStatus = 'ACCEPTED'" +
            "ORDER BY function ('SIZE', post.postVoteList) DESC")
    Page<Post> findAllByPostsActiveOrderByCountVote(Pageable page);

    @Query(value = "SELECT COUNT(post) " +
            "FROM Post post " +
            "WHERE post.isActive = true" +
            "   AND post.time <= CURRENT_TIMESTAMP" +
            "   AND post.moderationStatus = 'ACCEPTED'")
    long countAllActivePosts();

    @Query(value = "SELECT post " +
            "FROM Post post " +
            "WHERE post.isActive = true" +
            "   AND post.time <= CURRENT_TIMESTAMP" +
            "   AND post.moderationStatus = 'ACCEPTED'" +
            "   AND (LOWER(post.text) like %:query% OR LOWER(post.title) like %:query%)")
    Page<Post> findPosts(@Param("query") String query, Pageable pageable);

    @Query(value = "SELECT new com.skillbox.diplom.model.DTO.CountPostsDay(FUNCTION('DATE', post.time), COUNT(post))" +
            "FROM Post post " +
            "WHERE post.isActive = true" +
            "   AND post.time <= CURRENT_TIMESTAMP" +
            "   AND post.moderationStatus = 'ACCEPTED'" +
            "   AND FUNCTION('YEAR', post.time) <= :year " +
            "GROUP BY 1")
    List<CountPostsDay> getCountPostsInYear(@Param("year") int year);

    @Query(value = "SELECT post " +
            "FROM Post post " +
            "WHERE post.isActive = true" +
            "   AND post.time <= CURRENT_TIMESTAMP" +
            "   AND post.moderationStatus = 'ACCEPTED'" +
            "   AND FUNCTION('DATE', post.time) = FUNCTION('DATE', :date)")
    Page<Post> findPostByDate(@Param("date") LocalDate query, Pageable pageable);

    @Query(value = "SELECT post " +
            "FROM Post post INNER JOIN TagToPost tp " +
            "ON post = tp.post " +
            "WHERE post.isActive = true" +
            "   AND post.time <= CURRENT_TIMESTAMP" +
            "   AND post.moderationStatus = 'ACCEPTED'" +
            "   AND tp.tag.name = :tag ")
    Page<Post> findPostByTag(@Param("tag") String tag, Pageable paging);

    @Modifying
    @Query(value = "UPDATE Post post " +
            "SET post.isActive = true " +
            "WHERE post.isActive = false" +
            " AND post.time <= CURRENT_TIMESTAMP")
    void activationOfPosts();

    @Query(value = "SELECT COUNT(post) " +
            "FROM Post post " +
            "WHERE post.isActive = false" +
            " AND post.time <= CURRENT_TIMESTAMP")
    long countNoActivePosts();
}
