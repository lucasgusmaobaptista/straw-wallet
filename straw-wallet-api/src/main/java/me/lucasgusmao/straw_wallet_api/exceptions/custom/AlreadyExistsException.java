package me.lucasgusmao.straw_wallet_api.exceptions.custom;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
