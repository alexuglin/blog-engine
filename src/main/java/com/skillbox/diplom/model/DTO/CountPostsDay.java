package com.skillbox.diplom.model.DTO;

import com.skillbox.diplom.model.mappers.convert.DateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CountPostsDay {

    private LocalDate localDate;

    private Long count;

    public CountPostsDay(Date date, Long count) {
        DateConverter dateConverter = new DateConverter();
        this.localDate = dateConverter.dateToLocalDate(date);
        this.count = count;
    }

}
