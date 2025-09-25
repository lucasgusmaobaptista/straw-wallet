package me.lucasgusmao.straw_wallet_api.controller;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.expense.ExpenseRequest;
import me.lucasgusmao.straw_wallet_api.dto.expense.ExpenseResponse;
import me.lucasgusmao.straw_wallet_api.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService service;

    @PostMapping
    public ResponseEntity<ExpenseResponse> add(@RequestBody ExpenseRequest dto) {
        ExpenseResponse expense = service.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
    }
}
