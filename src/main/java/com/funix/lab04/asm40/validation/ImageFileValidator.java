package com.funix.lab04.asm40.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.funix.lab04.asm40.constraint.ValidImage;

public class ImageFileValidator implements ConstraintValidator<ValidImage, MultipartFile> {
    @Override
    public void initialize(final ValidImage constraint) { }

    @Override
    public boolean isValid(final MultipartFile multipartFile, final ConstraintValidatorContext context) {
        return !multipartFile.isEmpty();
    }
}
