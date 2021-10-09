package com.skillbox.diplom.model.mappers.convert;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class DateConverter {

    @Named("dateToLong")
    public Long dateToLong(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
