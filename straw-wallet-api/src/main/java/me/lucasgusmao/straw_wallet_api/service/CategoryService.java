package me.lucasgusmao.straw_wallet_api.service;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.CategoryDTO;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.AlreadyExistsException;
import me.lucasgusmao.straw_wallet_api.mappers.CategoryMapper;
import me.lucasgusmao.straw_wallet_api.model.Category;
import me.lucasgusmao.straw_wallet_api.model.User;
import me.lucasgusmao.straw_wallet_api.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final UserService userService;
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryDTO save(CategoryDTO dto) {
        User user = userService.getCurrentUSer();
        if (repository.existsByNameAndUserId(dto.name(), user.getId())) {
            throw new AlreadyExistsException("Já existe uma categoria com esse nome!");
        }
        Category category = mapper.toEntity(dto);
        category.setUser(user);
        Category savedCategpry = repository.save(category);
        return mapper.toDTO(savedCategpry);
    }
}
