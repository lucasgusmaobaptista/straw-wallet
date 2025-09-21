package me.lucasgusmao.straw_wallet_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.CategoryDTO;
import me.lucasgusmao.straw_wallet_api.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody @Valid CategoryDTO dto) {
        CategoryDTO savedCategory = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categories = service.findCurrentUserCategories();
        return ResponseEntity.ok().body(categories);
    }
}

