package me.lucasgusmao.straw_wallet_api.controller;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.expense.ExpenseResponse;
import me.lucasgusmao.straw_wallet_api.dto.income.IncomeResponse;
import me.lucasgusmao.straw_wallet_api.dto.other.FIlterDTO;
import me.lucasgusmao.straw_wallet_api.model.enums.CategoryType;
import me.lucasgusmao.straw_wallet_api.service.ExpenseService;
import me.lucasgusmao.straw_wallet_api.service.IncomeService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterController {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Object> filter(@RequestBody FIlterDTO filter) {
        LocalDate startDate = filter.startDate()!= null ? filter.startDate() : LocalDate.of(1970, 1, 1);
        LocalDate finalDate = filter.finalDate() != null ? filter.finalDate() : LocalDate.now();
        String keyword = filter.keyword() != null ? filter.keyword() : "";
        String sortField = filter.sortField() != null ? filter.sortField() : "date";
        Sort.Direction sortOrder = "desc".equalsIgnoreCase(filter.sortOrder()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortOrder, sortField);

        if (CategoryType.INCOME.equals(filter.type())) {
            List<IncomeResponse> incomes = incomeService.filter(startDate, finalDate, keyword, sort);
            return ResponseEntity.ok(incomes);
        } else if (CategoryType.EXPENSE.equals(filter.type())) {
            List<ExpenseResponse> expenses = expenseService.filter(startDate, finalDate, keyword, sort);
            return ResponseEntity.ok(expenses);
        } else {
            return ResponseEntity.badRequest().body("Tipo de categoria inválido. Use 'INCOME' ou 'EXPENSE'.");
        }
    }
}
