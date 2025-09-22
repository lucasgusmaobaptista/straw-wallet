package me.lucasgusmao.straw_wallet_api.mappers;

import me.lucasgusmao.straw_wallet_api.dto.user.UserRequest;
import me.lucasgusmao.straw_wallet_api.dto.user.UserResponse;
import me.lucasgusmao.straw_wallet_api.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequest userDTO);

    UserResponse toResponse(User user);
}