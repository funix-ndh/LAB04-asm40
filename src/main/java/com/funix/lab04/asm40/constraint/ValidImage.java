package com.funix.lab04.asm40.constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.funix.lab04.asm40.validation.ImageFileValidator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageFileValidator.class)
public @interface ValidImage {
    String message() default "Invalid image";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
