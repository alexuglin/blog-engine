package com.skillbox.diplom.service;

import com.skillbox.diplom.model.DTO.CountPostsDay;
import com.skillbox.diplom.model.api.response.CalendarResponse;
import com.skillbox.diplom.model.api.response.InitResponse;
import com.skillbox.diplom.model.enums.NameSetting;
import com.skillbox.diplom.model.enums.ValueSetting;
import com.skillbox.diplom.repository.GlobalSettingsRepository;
import com.skillbox.diplom.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfoService {

    private final GlobalSettingsRepository globalSettingsRepository;
    private final PostRepository postRepository;


    public ResponseEntity<InitResponse> getInfo() {
        return ResponseEntity.ok(new InitResponse());
    }


    public ResponseEntity<EnumMap<NameSetting, Boolean>> getSettings() {
        EnumMap<NameSetting, Boolean> booleanEnumMap = new EnumMap<>(NameSetting.class);
        globalSettingsRepository.findAll()
                .forEach(globalSetting ->
                        booleanEnumMap.put(globalSetting.getCode(), ValueSetting.YES == globalSetting.getValue()));
        return ResponseEntity.ok(booleanEnumMap);
    }

    public ResponseEntity<CalendarResponse> getCountPostsInDay(Integer year) {
        List<CountPostsDay> listCountPosts = postRepository.getCountPostsInYear(year);
        Set<Integer> years = listCountPosts
                .stream()
                .map(countPostsDay -> countPostsDay.getLocalDate().getYear())
                .collect(Collectors.toSet());
        Map<LocalDate, Long> mapCountPosts = listCountPosts
                .stream()
                .collect(Collectors.toMap(CountPostsDay::getLocalDate, CountPostsDay::getCount));
        return ResponseEntity.ok(new CalendarResponse(years, mapCountPosts));
    }


}
