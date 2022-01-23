package com.skillbox.diplom.model.mappers.convert;

import lombok.NoArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@NoArgsConstructor
public class DateConverter {

    @Named("dateToLong")
    public Long dateToLong(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    public static LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    public static LocalDate dateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Named("longToDateTime")
    public LocalDateTime longToDateTime(Long timestamp) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()).toLocalDateTime();
    }

    @Named("pastDateToCurrentDate")
    public LocalDateTime pastDateToCurrentDate(Long timestamp) {
        LocalDateTime checkDate = longToDateTime(timestamp);
        LocalDateTime currentDate = LocalDateTime.now();
        return checkDate.compareTo(currentDate) < 0 ? currentDate : checkDate;
    }

}
