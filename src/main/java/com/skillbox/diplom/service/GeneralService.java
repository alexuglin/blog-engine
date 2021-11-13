package com.skillbox.diplom.service;

import com.skillbox.diplom.model.DTO.CountPostsDay;
import com.skillbox.diplom.model.DTO.TagDTO;
import com.skillbox.diplom.model.TagToPost;
import com.skillbox.diplom.model.api.response.CalendarResponse;
import com.skillbox.diplom.model.api.response.InitResponse;
import com.skillbox.diplom.model.enums.NameSetting;
import com.skillbox.diplom.model.enums.ValueSetting;
import com.skillbox.diplom.model.mappers.TagMapper;
import com.skillbox.diplom.model.mappers.calculate.Calculator;
import com.skillbox.diplom.repository.GlobalSettingsRepository;
import com.skillbox.diplom.repository.PostRepository;
import com.skillbox.diplom.repository.TagToPostRepository;
import com.skillbox.diplom.util.enums.Constants;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralService {

    private final GlobalSettingsRepository globalSettingsRepository;
    private final PostRepository postRepository;
    private final TagToPostRepository tagToPostRepository;
    private final TagMapper tagMapper = Mappers.getMapper(TagMapper.class);


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

    public ResponseEntity<Map<String, List<TagDTO>>> getTags(String query) {
        long countPost = postRepository.countAllActivePosts();
        List<TagToPost> tagToPostList = tagToPostRepository.findTagToPostByActive(Objects.isNull(query) ? "" : query);
        int maxCountPosts = tagToPostRepository.getCountPostsFromTags();
        double weightMax = 1 / Calculator.weightTag(countPost, maxCountPosts);
        List<TagDTO> tagDTOList = tagToPostList
                .stream()
                .map(TagToPost::getTag)
                .distinct()
                .map(tag -> tagMapper.convertTo(tag, countPost, weightMax))
                .collect(Collectors.toList());
        Map<String, List<TagDTO>> tags = new HashMap<>();
        tags.put(Constants.TAGS.getValue(), tagDTOList);
        return ResponseEntity.ok(tags);
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
