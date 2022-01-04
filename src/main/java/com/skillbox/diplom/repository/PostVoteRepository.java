package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.Post;
import com.skillbox.diplom.model.PostVote;
import com.skillbox.diplom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostVoteRepository extends JpaRepository<PostVote, Integer> {

    Optional<PostVote> findPostVoteByPostAndUser(@Param("post") Post post, @Param("user") User user);
}
