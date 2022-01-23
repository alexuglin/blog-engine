package com.skillbox.diplom.model.validation.annotation;

import com.skillbox.diplom.model.validation.ExistsEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExistsEmail {
    String message() default "{This Email is exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
