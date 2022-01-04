package com.skillbox.diplom.controller;

import com.skillbox.diplom.exceptions.NotFoundPostException;
import com.skillbox.diplom.exceptions.NotFoundValue;
import com.skillbox.diplom.exceptions.WrongDataException;
import com.skillbox.diplom.exceptions.enums.Errors;
import com.skillbox.diplom.model.DTO.CommentDTO;
import com.skillbox.diplom.model.DTO.PostDTO;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import com.skillbox.diplom.util.UtilResponse;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.net.BindException;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class AdviceController {

    private final Logger logger = Logger.getLogger(AdviceController.class);

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

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        logger.warn(exception);
        ErrorResponse errorResponse = UtilResponse.getErrorResponse();
        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField,
                        fieldError -> Errors.findErrorsByFieldName(fieldError.getField()).getMessage()));
        errorResponse.setErrors(errors);
        Object target = exception.getBindingResult().getTarget();
        if (target instanceof CommentDTO) {
            errors.put(Errors.TEXT.getFieldName(), Errors.COMMENT.getMessage());
        }
        return target instanceof PostDTO ?
                ResponseEntity.ok(errorResponse) :
                ResponseEntity.badRequest().body(errorResponse);
    }
}
