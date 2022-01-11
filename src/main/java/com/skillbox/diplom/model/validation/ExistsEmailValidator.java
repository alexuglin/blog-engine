package com.skillbox.diplom.model.validation;

import com.skillbox.diplom.model.User;
import com.skillbox.diplom.model.validation.annotation.ExistsEmail;
import com.skillbox.diplom.repository.UserRepository;
import com.skillbox.diplom.util.UserUtility;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class ExistsEmailValidator implements ConstraintValidator<ExistsEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(email)) {
            return false;
        }
        String currentUserEmail = UserUtility.getCurrentUserEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.isEmpty() || Objects.equals(optionalUser.get().getEmail(), currentUserEmail);
    }
}
