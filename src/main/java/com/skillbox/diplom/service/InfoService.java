package com.skillbox.diplom.service;

import com.skillbox.diplom.model.DTO.CountPostsDay;
import com.skillbox.diplom.model.DTO.StatisticsPost;
import com.skillbox.diplom.model.GlobalSetting;
import com.skillbox.diplom.model.User;
import com.skillbox.diplom.model.api.response.CalendarResponse;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.model.api.response.InitResponse;
import com.skillbox.diplom.model.api.response.StatisticsBlog;
import com.skillbox.diplom.model.enums.NameSetting;
import com.skillbox.diplom.model.enums.ValueSetting;
import com.skillbox.diplom.model.mappers.StatisticsMapper;
import com.skillbox.diplom.repository.GlobalSettingsRepository;
import com.skillbox.diplom.repository.PostRepository;
import com.skillbox.diplom.repository.PostVoteRepository;
import com.skillbox.diplom.util.UserUtility;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfoService {

    private final PostRepository postRepository;
    private final PostVoteRepository postVoteRepository;
    private final GlobalSettingsRepository globalSettingsRepository;
    private final UserUtility userUtility;
    private final StatisticsMapper statisticsMapper = Mappers.getMapper(StatisticsMapper.class);


    public ResponseEntity<InitResponse> getInfo() {
        return ResponseEntity.ok(new InitResponse());
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

    public ResponseEntity<StatisticsBlog> getMyStatistics() {
        User user = userUtility.getCurrentUser();
        return ResponseEntity.ok(getStatisticsBlog(user));
    }

    public ResponseEntity<StatisticsBlog> getStatistics() {
        User user = userUtility.getCurrentUser();
        GlobalSetting globalSetting = globalSettingsRepository.findGlobalSettingByCode(NameSetting.STATISTICS_IS_PUBLIC);
        if (globalSetting.getValue() == ValueSetting.NO && (Objects.isNull(user) || !user.isModerator())) {
            throw new AuthorizationServiceException("Статистика блога закрыта модератором");
        }
        return ResponseEntity.ok(getStatisticsBlog(null));
    }

    private StatisticsBlog getStatisticsBlog(User user) {
        StatisticsPost statisticsPost = Objects.isNull(user) ?
                postRepository.getStatisticsPost() : postRepository.getStatisticsPostByUserId(user.getId());
        long likesCount = Objects.isNull(user) ?
                postVoteRepository.getCountVotes((byte) 1) : postVoteRepository.getCountVotesByUserId(user.getId(), (byte) 1);
        long disLikesCount = Objects.isNull(user) ?
                postVoteRepository.getCountVotes((byte) -1) : postVoteRepository.getCountVotesByUserId(user.getId(), (byte) -1);
        return statisticsMapper.convertTo(statisticsPost, likesCount, disLikesCount);
    }
}
