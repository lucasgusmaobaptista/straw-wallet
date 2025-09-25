package me.lucasgusmao.straw_wallet_api.controller;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.income.IncomeRequest;
import me.lucasgusmao.straw_wallet_api.dto.income.IncomeResponse;
import me.lucasgusmao.straw_wallet_api.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping()
    public ResponseEntity<List<IncomeResponse>> get() {
        List<IncomeResponse> incomes = service.getUserCurrentMonth();
        return ResponseEntity.ok(incomes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
