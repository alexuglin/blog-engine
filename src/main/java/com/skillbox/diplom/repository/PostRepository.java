package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.DTO.CountPostsDay;
import com.skillbox.diplom.model.DTO.StatisticsPost;
import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.enums.ModerationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    long countPostByModerationStatusAndIsActiveAndTimeLessThanEqual(ModerationStatus moderationStatus, boolean active, LocalDateTime time);

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

    int countPostByModerationStatusAndIsActive(ModerationStatus moderationStatus, boolean active);

    @Query(value = "SELECT post " +
            "FROM Post post " +
            "WHERE post.isActive = true" +
            "   AND post.moderationStatus = :moderationStatus" +
            "   AND post.user.email = :email ")
    Page<Post> findAllByPostByUserEmail(@Param("email") String email, @Param("moderationStatus") ModerationStatus moderationStatus, Pageable pageable);

    @Query(value = "SELECT post " +
            "FROM Post post " +
            "WHERE post.isActive = false" +
            "   AND post.user.email = :email ")
    Page<Post> findAllByPostNotActiveByUserEmail(@Param("email") String email, Pageable pageable);

    @Query(value = "SELECT post " +
            "FROM Post post  " +
            "WHERE post.isActive = true" +
            "   AND post.moderationStatus = :moderationStatus" +
            "   AND post.moderator.email = :email ")
    Page<Post> findAllByPostByModeratorEmail(@Param("email") String email, @Param("moderationStatus") ModerationStatus moderationStatus, Pageable pageable);

    @Query(value = "SELECT post " +
            "FROM Post post " +
            "WHERE post.isActive = true" +
            "   AND post.moderationStatus = :moderationStatus")
    Page<Post> findAllByPostByModerationStatus(@Param("moderationStatus") ModerationStatus moderationStatus, Pageable pageable);

    @Query("SELECT new com.skillbox.diplom.model.DTO.StatisticsPost(COUNT(post), SUM(post.viewCount), MIN(post.time)) " +
            "from Post post " +
            "WHERE post.user.id = :userId " +
            "AND post.moderationStatus = 'ACCEPTED' " +
            "AND post.isActive = true " +
            "AND post.time <= CURRENT_TIMESTAMP")
    StatisticsPost getStatisticsPostByUserId(@Param("userId") Integer userId);

    @Query("SELECT new com.skillbox.diplom.model.DTO.StatisticsPost(COUNT(post), SUM(post.viewCount), MIN(post.time)) " +
            "from Post post " +
            "WHERE post.moderationStatus = 'ACCEPTED' " +
            "AND post.isActive = true " +
            "AND post.time <= CURRENT_TIMESTAMP")
    StatisticsPost getStatisticsPost();
}
