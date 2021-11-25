package com.skillbox.diplom.repository;

import com.skillbox.diplom.model.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CaptchaCodeRepository extends JpaRepository<CaptchaCode, Integer> {

    @Modifying
    @Query(value = "DELETE FROM captcha_codes captcha" +
            " WHERE DATE_ADD(captcha.time, INTERVAL :timePeriod SECOND ) <= CURRENT_TIMESTAMP()"
            , nativeQuery = true)
    void deleteCaptchaCodeBy(@Param("timePeriod") Integer timePeriod);

    CaptchaCode findBySecretCode(String secretCode);
}
