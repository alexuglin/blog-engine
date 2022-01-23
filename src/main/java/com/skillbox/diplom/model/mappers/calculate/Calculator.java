package com.skillbox.diplom.model.mappers.calculate;

import com.skillbox.diplom.model.Tag;
import com.skillbox.diplom.model.enums.ModerationStatus;

import java.time.LocalDateTime;

public class Calculator {

    public static double weightTag(long countAllActivePost, long countPostsFromTag) {
        return (double) countPostsFromTag / countAllActivePost;
    }

    public static long countPostsCurrentTag(Tag tag) {
        return tag
                .getPostList()
                .stream()
                .filter(post -> post.isActive()
                        && post.getModerationStatus() == ModerationStatus.ACCEPTED
                        && post.getTime().compareTo(LocalDateTime.now()) < 1)
                .count();
    }
}
