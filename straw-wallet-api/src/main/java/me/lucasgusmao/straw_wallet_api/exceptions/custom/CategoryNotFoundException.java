package me.lucasgusmao.straw_wallet_api.exceptions.custom;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
