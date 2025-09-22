package me.lucasgusmao.straw_wallet_api.exceptions.handler;

import jakarta.persistence.EntityNotFoundException;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.AlreadyExistsException;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.CategoryNotFoundException;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.EmailSendException;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.InvalidCredentialsException;
import me.lucasgusmao.straw_wallet_api.exceptions.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseError(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleAlreadyExistsException(AlreadyExistsException e) {
        return ResponseError.conflict(e.getMessage());
    }

    @ExceptionHandler(EmailSendException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleEmailSendException(EmailSendException e) {
        return new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro ao enviar email: " + e.getMessage(), List.of());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleUsernameNotFoundException(Exception e) {
        return new ResponseError(HttpStatus.NOT_FOUND.value(), "Erro ao encontrar usuário: " + e.getMessage(), List.of());
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleInvalidCredentials(InvalidCredentialsException e) {
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Credenciais inválidas: " + e.getMessage(), List.of());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleCategoryNotFoundException(CategoryNotFoundException e) {
        return new ResponseError(HttpStatus.NOT_FOUND.value(), "Categoria não encontrada: " + e.getMessage(), List.of());
    }

}
