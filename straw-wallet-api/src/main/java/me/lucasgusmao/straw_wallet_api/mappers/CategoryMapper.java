package me.lucasgusmao.straw_wallet_api.mappers;

import me.lucasgusmao.straw_wallet_api.dto.category.CategoryRequest;
import me.lucasgusmao.straw_wallet_api.dto.category.CategoryResponse;
import me.lucasgusmao.straw_wallet_api.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toEntity(CategoryRequest dto);

    @Mapping(source = "user.id", target = "userId")
    CategoryResponse toResponse(Category entity);


}