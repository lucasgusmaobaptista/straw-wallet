package me.lucasgusmao.straw_wallet_api.mappers;

import me.lucasgusmao.straw_wallet_api.dto.UserDTO;
import me.lucasgusmao.straw_wallet_api.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDTO);

    UserDTO toDTO(User user);
}