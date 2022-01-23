package com.skillbox.diplom.model.validation.annotation;

import com.skillbox.diplom.model.validation.ConstraintImageValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConstraintImageValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConstraintImage {

    String message() default "Размер изображения(фото) превышает %s либо не верный формат(допустимы: %s)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
