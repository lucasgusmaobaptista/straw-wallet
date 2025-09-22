package me.lucasgusmao.straw_wallet_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.dto.category.CategoryRequest;
import me.lucasgusmao.straw_wallet_api.dto.category.CategoryResponse;
import me.lucasgusmao.straw_wallet_api.model.CategoryType;
import me.lucasgusmao.straw_wallet_api.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody @Valid CategoryRequest dto) {
        CategoryResponse savedCategory = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<CategoryResponse> categories = service.findCurrentUserCategories();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<CategoryResponse>> getCurrentUserCategoriesByType(@PathVariable String type) {
        CategoryType categoryType = CategoryType.valueOf(type.toUpperCase());
        List<CategoryResponse> categories = service.findCurrentUserCategoriesByType(categoryType);
        return ResponseEntity.ok().body(categories);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable UUID id, @RequestBody @Valid CategoryRequest dto) {
        CategoryResponse updatedCategory = service.update(id, dto);
        return ResponseEntity.ok().body(updatedCategory);
    }
}

