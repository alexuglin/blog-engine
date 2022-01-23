package com.skillbox.diplom.service;

import com.skillbox.diplom.model.api.response.InitResponse;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final Logger logger = Logger.getLogger(EmailService.class);

    public void sendMessage(String to, String subject, String textLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        InitResponse initResponse = new InitResponse();
        helper.setFrom(initResponse.getTitle() + "<" + initResponse.getEmail() + ">");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(getMessage(textLink), true);
        mailSender.send(message);
        logger.info("sendMessage success!");
    }

    private String getMessage(String link) {
        return "Для востановления пароля перейдите <a href=\"" + link + "\"> по ссылке </a>";
    }
}
