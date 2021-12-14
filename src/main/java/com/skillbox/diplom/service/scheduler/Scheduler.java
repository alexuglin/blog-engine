package com.skillbox.diplom.service.scheduler;

import com.skillbox.diplom.repository.CaptchaCodeRepository;
import com.skillbox.diplom.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Scheduler {

    @Value("${scheduling.deletingCodeCaptcha}")
    private Integer deletingCodeCaptcha;

    private final PostRepository postRepository;
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
}
