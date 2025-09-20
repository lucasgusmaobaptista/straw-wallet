package me.lucasgusmao.straw_wallet_api.exceptions.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ResponseError(int status, String message, List<ErrorField> errors ) {

    public static ResponseError defaultResponseError(String message) {
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ResponseError conflict(String message) {
        return new ResponseError(HttpStatus.CONFLICT.value(), message, List.of());
    }
}
