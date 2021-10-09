package com.skillbox.diplom.model.mappers.calculate;

public class Calculator {

    public static double weightTag(long countAllActivePost, int countPostsFromTag) {
        return (double) countPostsFromTag / countAllActivePost;
    }
}
