package com.skillbox.diplom.service.scheduler;

import com.skillbox.diplom.model.Tag;
import com.skillbox.diplom.repository.CaptchaCodeRepository;
import com.skillbox.diplom.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    @Value("${scheduling.deletingCodeCaptcha}")
    private Integer deletingCodeCaptcha;

    private final TagRepository tagRepository;
    private final CaptchaCodeRepository captchaCodeRepository;

    private final Logger logger = Logger.getLogger(Scheduler.class);

    /**
     * Удаление каждую секунду просроченных капчей
     */
    @Scheduled(fixedRate = 1000)
    @Transactional
    public void deletingCodeCaptcha() {
        captchaCodeRepository.deleteCaptchaCodeBy(deletingCodeCaptcha);
    }

    /**
     * Удаление каждые fixedRate неиспользуемых тегов
     */
    @Scheduled(fixedRate = 1800000)
    @Transactional
    public void deleteUnusedTags() {
        List<Tag> tagList = tagRepository.selectUnusedTags();
        tagRepository.deleteAll(tagList);
        logger.info("Delete unused tags: " + tagList);
    }
}
