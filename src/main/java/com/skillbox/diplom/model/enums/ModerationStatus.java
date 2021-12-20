package com.skillbox.diplom.model.enums;

import com.skillbox.diplom.exceptions.NotFoundModerationStatus;

import java.util.Arrays;
import java.util.List;

public enum ModerationStatus {

    NEW (List.of("pending", "new")),
    ACCEPTED(List.of("published", "accepted")),
    DECLINED(List.of("declined"));

    private final List<String> descriptions;

    ModerationStatus(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public static ModerationStatus findModerationStatusByStatus(String status) {
       return Arrays
               .stream(values())
               .filter(v -> v.getDescriptions().contains(status))
               .findFirst()
               .orElseThrow(() -> new NotFoundModerationStatus("Not found moderation status " + status));
    }
}