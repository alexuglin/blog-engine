package com.skillbox.diplom.model.validation.annotation;

import com.skillbox.diplom.model.validation.ConstraintNameValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConstraintNameValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConstraintName {

    String message() default "{Name is not correct}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
