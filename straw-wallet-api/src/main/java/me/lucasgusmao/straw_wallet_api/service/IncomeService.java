package me.lucasgusmao.straw_wallet_api.service;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.income.IncomeRequest;
import me.lucasgusmao.straw_wallet_api.dto.income.IncomeResponse;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.CategoryNotFoundException;
import me.lucasgusmao.straw_wallet_api.mappers.IncomeMapper;
import me.lucasgusmao.straw_wallet_api.model.Category;
import me.lucasgusmao.straw_wallet_api.model.Income;
import me.lucasgusmao.straw_wallet_api.model.User;
import me.lucasgusmao.straw_wallet_api.repository.CategoryRepository;
import me.lucasgusmao.straw_wallet_api.repository.IncomeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository repository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final IncomeMapper mapper;

    public IncomeResponse add(IncomeRequest dto) {
        User user = userService.getCurrentUser();
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));
        Income newExpense = mapper.toEntity(dto);
        newExpense = repository.save(newExpense);

        return mapper.toResponse(newExpense);
    }
}
