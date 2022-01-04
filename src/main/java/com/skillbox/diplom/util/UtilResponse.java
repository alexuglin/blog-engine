package com.skillbox.diplom.util;

import com.skillbox.diplom.model.api.response.ErrorResponse;

import java.util.Map;

public class UtilResponse {

    public static ErrorResponse getErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setResult(false);
        return errorResponse;
    }

    public static ErrorResponse getErrorResponse(Map<String, String> errors) {
        ErrorResponse errorResponse = getErrorResponse();
        errorResponse.setErrors(errors);
        return errorResponse;
    }
}
