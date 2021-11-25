package com.skillbox.diplom.exceptions;

import com.skillbox.diplom.model.api.response.ErrorResponse;
import lombok.ToString;

@ToString
public class WrongDataException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public WrongDataException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
