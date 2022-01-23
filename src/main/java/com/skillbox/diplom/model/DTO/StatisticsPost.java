package com.skillbox.diplom.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsPost {

    private long postsCount;

    private long viewsCount;

    private LocalDateTime firstPublication;
}
