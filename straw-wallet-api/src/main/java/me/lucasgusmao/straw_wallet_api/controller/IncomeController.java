package me.lucasgusmao.straw_wallet_api.controller;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.expense.ExpenseRequest;
import me.lucasgusmao.straw_wallet_api.dto.expense.ExpenseResponse;
import me.lucasgusmao.straw_wallet_api.dto.income.IncomeRequest;
import me.lucasgusmao.straw_wallet_api.dto.income.IncomeResponse;
import me.lucasgusmao.straw_wallet_api.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService service;

    @PostMapping
    public ResponseEntity<IncomeResponse> add(@RequestBody IncomeRequest dto) {
        IncomeResponse income = service.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(income);
    }
}
