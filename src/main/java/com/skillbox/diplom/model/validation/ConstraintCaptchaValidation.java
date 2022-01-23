package com.skillbox.diplom.model.validation;

import com.skillbox.diplom.model.CaptchaCode;
import com.skillbox.diplom.model.api.request.UserRequest;
import com.skillbox.diplom.model.validation.annotation.ConstraintCaptcha;
import com.skillbox.diplom.repository.CaptchaCodeRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.Objects;

@RequiredArgsConstructor
@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class ConstraintCaptchaValidation implements ConstraintValidator<ConstraintCaptcha, Object> {

    private final CaptchaCodeRepository captchaCodeRepository;

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(o) || !(o instanceof UserRequest)) {
            return true;
        }
        UserRequest userRequest = (UserRequest) o;
        if (Objects.isNull(userRequest.getCaptcha()) || Objects.isNull(userRequest.getCaptchaSecret())) {
            return false;
        }
        CaptchaCode captchaCode = captchaCodeRepository.findBySecretCode(userRequest.getCaptchaSecret());
        return userRequest.getCaptcha().equals(captchaCode.getCode());
    }
}
