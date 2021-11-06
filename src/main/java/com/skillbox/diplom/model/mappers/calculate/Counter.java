package com.skillbox.diplom.model.mappers.calculate;

import com.skillbox.diplom.model.PostVote;
import org.mapstruct.Named;

import java.util.List;

public class Counter {

    public static int calculateVote(List<PostVote> postVotes, int voteValue) {
        return (int) postVotes.stream()
                .filter(postVote -> postVote.getValue() == voteValue).count();
    }

    @Named("calculateCountElementsList")
    public int calculateCountElementsList(List<?> list) {
        return list.size();
    }
}
