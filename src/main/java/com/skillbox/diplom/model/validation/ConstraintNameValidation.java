package com.skillbox.diplom.model.validation;

import com.skillbox.diplom.model.validation.annotation.ConstraintName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class ConstraintNameValidation implements ConstraintValidator<ConstraintName, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name.matches("^[А-ЯЁA-Z][A-Za-zа-яёА-ЯЁ\\s-]{1,254}");
    }
}
