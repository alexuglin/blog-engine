package com.skillbox.diplom.controller;

import com.skillbox.diplom.exceptions.NotFoundPostException;
import com.skillbox.diplom.exceptions.WrongDataException;
import com.skillbox.diplom.model.api.response.ErrorResponse;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AdviceController {

    private final Logger logger = Logger.getLogger(AdviceController.class);

    @ExceptionHandler(NotFoundPostException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleRegisterRequestException(NotFoundPostException exception) {
        logger.warn(exception);
        return exception.getMessage();
    }

    @ExceptionHandler(WrongDataException.class)
    @ResponseBody
    public ErrorResponse handleWrongDateRequestException(WrongDataException exception) {
        logger.warn(exception);
        return exception.getErrorResponse();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ErrorResponse handleAuthenticationException(AuthenticationException exception) {
        logger.warn(exception);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setResult(false);
        return errorResponse;
    }
}
