package me.lucasgusmao.straw_wallet_api.event;

import me.lucasgusmao.straw_wallet_api.model.User;

public record UserRegisteredEvent(User user) {
}
