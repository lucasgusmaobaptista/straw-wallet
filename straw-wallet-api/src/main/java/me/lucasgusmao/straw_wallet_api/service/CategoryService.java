package me.lucasgusmao.straw_wallet_api.service;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.category.CategoryRequest;
import me.lucasgusmao.straw_wallet_api.dto.category.CategoryResponse;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.AlreadyExistsException;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.CategoryNotFoundException;
import me.lucasgusmao.straw_wallet_api.mappers.CategoryMapper;
import me.lucasgusmao.straw_wallet_api.model.Category;
import me.lucasgusmao.straw_wallet_api.model.CategoryType;
import me.lucasgusmao.straw_wallet_api.model.User;
import me.lucasgusmao.straw_wallet_api.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final UserService userService;
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryResponse save(CategoryRequest dto) {
        User user = userService.getCurrentUser();
        if (repository.existsByNameAndUserId(dto.name(), user.getId())) {
            throw new AlreadyExistsException("Já existe uma categoria com esse nome!");
        }
        Category category = mapper.toEntity(dto);
        category.setUser(user);
        Category savedCategory = repository.save(category);
        return mapper.toResponse(savedCategory);
    }

    public List<CategoryResponse> findCurrentUserCategories() {
        User user = userService.getCurrentUser();
        List<Category> categories = repository.findByUserId(user.getId());
        return categories.stream().map(mapper::toResponse).toList();
    }

    public List<CategoryResponse> findCurrentUserCategoriesByType(CategoryType type) {
        User user = userService.getCurrentUser();
        List<Category> categories = repository.findByTypeAndUserId(type, user.getId());
        return categories.stream().map(mapper::toResponse).toList();
    }

    public CategoryResponse update(UUID id, CategoryRequest dto) {
        User user = userService.getCurrentUser();
        Category categoryFound = repository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada!"));

        categoryFound.setName(dto.name());
        categoryFound.setType(dto.type());
        categoryFound.setIcon(dto.icon());
        return mapper.toResponse(repository.save(categoryFound));
    }

}
