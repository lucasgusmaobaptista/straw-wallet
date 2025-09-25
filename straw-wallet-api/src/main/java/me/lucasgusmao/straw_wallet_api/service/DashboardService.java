package me.lucasgusmao.straw_wallet_api.service;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.RecentTransactionDTO;
import me.lucasgusmao.straw_wallet_api.dto.expense.ExpenseResponse;
import me.lucasgusmao.straw_wallet_api.dto.income.IncomeResponse;
import me.lucasgusmao.straw_wallet_api.model.User;
import me.lucasgusmao.straw_wallet_api.model.enums.CategoryType;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Stream.concat;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final UserService userService;

    public Map<String, Object> getDashboardData() {
        User user = userService.getCurrentUser();
        Map<String, Object> dashboardData = new LinkedHashMap<>();
        List<IncomeResponse> latestIncomes = incomeService.getUserLatest5Expenses();
        List< ExpenseResponse> latestExpenses = expenseService.getUserLatest5Expenses();
        List<RecentTransactionDTO> recentTransactions = concat(latestIncomes.stream().map(income -> RecentTransactionDTO.builder()
                        .id(income.id())
                        .userId(user.getId())
                        .icon(income.icon())
                        .name(income.name())
                        .amount(income.amount())
                        .date(income.date())
                        .createdAt(income.createdAt())
                        .updatedAt(income.updatedAt())
                        .categoryType(CategoryType.INCOME).build()),
                latestExpenses.stream().map(expense -> RecentTransactionDTO.builder()
                        .id(expense.id())
                        .userId(user.getId())
                        .icon(expense.icon())
                        .name(expense.name())
                        .amount(expense.amount())
                        .date(expense.date())
                        .createdAt(expense.createdAt())
                        .updatedAt(expense.updatedAt())
                        .categoryType(CategoryType.EXPENSE).build()))
                .sorted((a, b) -> {
                    int cmp = b.date().compareTo(a.date());
                    if (cmp == 0 && a.createdAt() != null && b.createdAt() != null) {
                        return b.createdAt().compareTo(a.createdAt());
                    }
                    return cmp;
                }).collect(Collectors.toList());
        dashboardData.put("totalBalance", incomeService.getUserTotal().subtract(expenseService.getUserTotal()));
        dashboardData.put("totalIncome", incomeService.getUserTotal());
        dashboardData.put("totalExpenses", expenseService.getUserTotal());
        dashboardData.put("recent5Expenses", latestExpenses);
        dashboardData.put("recent5Incomes", latestIncomes);
        dashboardData.put("recentTransactions", recentTransactions);
        return dashboardData;
    }

}
