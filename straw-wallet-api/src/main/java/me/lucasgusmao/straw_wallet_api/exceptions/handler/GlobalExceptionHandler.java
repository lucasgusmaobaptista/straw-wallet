package me.lucasgusmao.straw_wallet_api.exceptions.handler;

import me.lucasgusmao.straw_wallet_api.exceptions.custom.AlreadyExistsException;
import me.lucasgusmao.straw_wallet_api.exceptions.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleAlreadyExistsException(AlreadyExistsException e) {
        return ResponseError.conflict(e.getMessage());
    }
}
