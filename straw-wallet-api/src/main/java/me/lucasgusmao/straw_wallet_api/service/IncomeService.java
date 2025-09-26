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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository repository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final IncomeMapper mapper;

    @Transactional
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

    public void delete(UUID id) {
        User user = userService.getCurrentUser();
        Income expense = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Renda não encontrada"));
        if(!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Você não tem permissão para deletar essa renda");
        }
        repository.deleteById(id);
    }
    public List<IncomeResponse>  getUserLatest5Expenses() {
        User user = userService.getCurrentUser();
        List<Income> incomes = repository.findByUserIdOrderByDateDesc(user.getId());
        return incomes.stream().limit(5).map(mapper::toResponse).toList();
    }

    public BigDecimal getUserTotal() {
        User user = userService.getCurrentUser();
        BigDecimal totalIncomesByUserId = repository.findTotalIncomesByUserId(user.getId());
        return totalIncomesByUserId != null ? totalIncomesByUserId : BigDecimal.ZERO;
    }

    public List<IncomeResponse> filter(LocalDate startDate, LocalDate endDate, String keyword, Sort sort) {
        User user = userService.getCurrentUser();
        List<Income> incomes = repository.findByUserIdAndDateBetweenAndNameContainingIgnoreCase(user.getId(), startDate, endDate, keyword, sort);
        return incomes.stream().map(mapper::toResponse).toList();
    }
}
