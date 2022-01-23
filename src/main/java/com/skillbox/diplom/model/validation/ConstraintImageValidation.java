package com.skillbox.diplom.model.validation;

import com.skillbox.diplom.model.validation.annotation.ConstraintImage;
import com.skillbox.diplom.util.FileStorageProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.Objects;

@RequiredArgsConstructor
@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class ConstraintImageValidation implements ConstraintValidator<ConstraintImage, Object> {

    private final FileStorageProperties fileProperties;

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {

        if (Objects.isNull(obj) || (obj instanceof String && ((String) obj).isBlank())) {
            return true;
        }

        if (obj instanceof MultipartFile) {
            MultipartFile image = (MultipartFile) obj;
            String ext = Objects.isNull(image.getOriginalFilename()) ? "" : FilenameUtils.getExtension(image.getOriginalFilename());
            return fileProperties.getExtensions().contains(ext) && image.getSize() <= fileProperties.getSizeFileToByte();
        }

        return false;
    }
}
