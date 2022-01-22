package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.PostVote;
import com.skillbox.diplom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostVoteRepository extends JpaRepository<PostVote, Integer> {

    Optional<PostVote> findPostVoteByPostAndUser(@Param("post") Post post, @Param("user") User user);


    @Query(value = "SELECT count(vote.value)" +
            "FROM PostVote vote " +
            "WHERE vote.user.id = :userId " +
            "AND vote.value = :valueVote " +
            "AND vote.post.isActive = true " +
            "AND vote.post.time <= CURRENT_TIMESTAMP " +
            "AND vote.post.moderationStatus = 'ACCEPTED'")
    long getCountVotesByUserId(@Param("userId") int userId, @Param("valueVote") byte valueVote);

    @Query(value = "SELECT count(vote.value)" +
            "FROM PostVote vote " +
            "WHERE vote.value = :valueVote " +
            "AND vote.post.isActive = true " +
            "AND vote.post.time <= CURRENT_TIMESTAMP " +
            "AND vote.post.moderationStatus = 'ACCEPTED'")
    long getCountVotes(@Param("valueVote") byte valueVote);

}
