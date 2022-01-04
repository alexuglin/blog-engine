package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {
}
