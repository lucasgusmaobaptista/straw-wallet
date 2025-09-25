package me.lucasgusmao.straw_wallet_api.service;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.income.IncomeRequest;
import me.lucasgusmao.straw_wallet_api.dto.income.IncomeResponse;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.CategoryNotFoundException;
import me.lucasgusmao.straw_wallet_api.mappers.IncomeMapper;
import me.lucasgusmao.straw_wallet_api.model.Category;
import me.lucasgusmao.straw_wallet_api.model.Expense;
import me.lucasgusmao.straw_wallet_api.model.Income;
import me.lucasgusmao.straw_wallet_api.model.User;
import me.lucasgusmao.straw_wallet_api.repository.CategoryRepository;
import me.lucasgusmao.straw_wallet_api.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        newExpense.setCategory(category);
        newExpense.setUser(user);
        newExpense = repository.save(newExpense);

        return mapper.toResponse(newExpense);
    }

    public List<IncomeResponse> getUserCurrentMonth() {
        User user = userService.getCurrentUser();
        LocalDate date = LocalDate.now();
        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate finalDate = date.withDayOfMonth(date.lengthOfMonth());
        List<Income> result = repository.findByUserIdAndDateBetween(user.getId(), startDate, finalDate);
        return result.stream().map(mapper::toResponse).toList();
    }
}
