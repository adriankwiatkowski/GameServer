package com.example.client.utils;

import retrofit2.Response;

import java.io.IOException;

public class ErrorHandler {
    public static <T> String ErrorHandler(Response<T> response) {
        if (response.errorBody() != null) {
            String errorBodyString = null;
            try {
                errorBodyString = response.errorBody().string();
            } catch (IOException e) {
                mapHttpCode(response.code());
            }

            if (errorBodyString != null && !errorBodyString.isEmpty()) {
                return errorBodyString;
            }
        }
        return mapHttpCode(response.code());
    }

    public static String mapHttpCode(int httpCode) {
        switch (httpCode) {
            case 400:
                return "Bad Request";
            case 401:
                return "Unauthorized";
            case 403:
                return "Forbidden";
            case 404:
                return "Not Found";
            default:
                return "Unknown Error";
        }
    }
}
