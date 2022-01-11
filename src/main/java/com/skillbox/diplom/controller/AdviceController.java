package com.skillbox.diplom.controller;

import com.skillbox.diplom.exceptions.NotFoundPostException;
import com.skillbox.diplom.exceptions.NotFoundValue;
import com.skillbox.diplom.exceptions.WrongDataException;
import com.skillbox.diplom.model.DTO.PostDTO;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.util.FileStorageProperties;
import com.skillbox.diplom.util.UtilResponse;
import com.skillbox.diplom.util.enums.FieldName;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.net.BindException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class AdviceController {

    private final Logger logger = Logger.getLogger(AdviceController.class);
    private final FileStorageProperties fileStorageProperties;


    @ExceptionHandler({NotFoundPostException.class, NotFoundValue.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleRegisterRequestException(NotFoundPostException exception) {
        logger.warn(exception);
        return exception.getMessage();
    }

    @ExceptionHandler(WrongDataException.class)
    public ResponseEntity<ErrorResponse> handleWrongDateRequestException(WrongDataException exception) {
        logger.warn(exception);
        return ResponseEntity.badRequest().body(exception.getErrorResponse());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ErrorResponse handleAuthenticationException(AuthenticationException exception) {
        logger.warn(exception);
        return UtilResponse.getErrorResponse();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> onConstraintValidationException(ConstraintViolationException exception) {
        ErrorResponse errorResponse = UtilResponse.getErrorResponse();
        Map<String, String> errors = exception
                .getConstraintViolations()
                .stream().collect(Collectors
                        .toMap(v -> {
                            String path = v.getPropertyPath().toString();
                            return path.substring(path.lastIndexOf(".") + 1);
                        }, v -> String.format(v.getMessage(), fileStorageProperties.getMaxSize(),
                                fileStorageProperties.getExtensions())));
        errorResponse.setErrors(errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        logger.warn(exception);
        Object target = exception.getBindingResult().getTarget();
        ErrorResponse errorResponse = UtilResponse.getErrorResponse();
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach(objectError -> {
                    String codes = objectError.getCode();
                    String nameField = objectError instanceof FieldError ?
                            ((FieldError) objectError).getField() :
                            !Objects.isNull(codes) && codes.contains("ConstraintCaptcha") ?
                                    FieldName.CAPTCHA.getDescription() :
                                    "Wrong data";
                    errors.put(nameField, objectError.getDefaultMessage());
                });
        errorResponse.setErrors(errors);
        return target instanceof PostDTO ?
                ResponseEntity.ok(errorResponse) :
                ResponseEntity.badRequest().body(errorResponse);
    }
}
