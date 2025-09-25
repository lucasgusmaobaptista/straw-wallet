package me.lucasgusmao.straw_wallet_api.service;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.expense.ExpenseRequest;
import me.lucasgusmao.straw_wallet_api.dto.expense.ExpenseResponse;
import me.lucasgusmao.straw_wallet_api.exceptions.custom.CategoryNotFoundException;
import me.lucasgusmao.straw_wallet_api.mappers.ExpenseMapper;
import me.lucasgusmao.straw_wallet_api.model.Category;
import me.lucasgusmao.straw_wallet_api.model.Expense;
import me.lucasgusmao.straw_wallet_api.model.User;
import me.lucasgusmao.straw_wallet_api.repository.CategoryRepository;
import me.lucasgusmao.straw_wallet_api.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository repository;
    private final CategoryRepository categoryRepository;
    private  final UserService userService;
    private final ExpenseMapper mapper;

    public ExpenseResponse add(ExpenseRequest dto) {
        User user = userService.getCurrentUser();
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));
        Expense newExpense = mapper.toEntity(dto);
        newExpense.setCategory(category);
        newExpense.setUser(user);
        newExpense = repository.save(newExpense);

        return mapper.toResponse(newExpense);
    }

    public List<ExpenseResponse> getUserCurrentMonth() {
        User user = userService.getCurrentUser();
        LocalDate date = LocalDate.now();
        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate finalDate = date.withDayOfMonth(date.lengthOfMonth());
        List<Expense> result = repository.findByUserIdAndDateBetween(user.getId(), startDate, finalDate);
        return result.stream().map(mapper::toResponse).toList();
    }

    public void delete(UUID id) {
        User user = userService.getCurrentUser();
        Expense expense = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
        if(!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Você não tem permissão para deletar essa despesa");
        }
        repository.deleteById(id);
    }
}
