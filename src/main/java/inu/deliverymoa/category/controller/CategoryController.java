package inu.deliverymoa.category.controller;

import inu.deliverymoa.category.dto.CategoryCreateRequest;
import inu.deliverymoa.category.dto.CategoryResponse;
import inu.deliverymoa.category.dto.CategoryUpdateRequest;
import inu.deliverymoa.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<Void> createCategory(@RequestBody @Valid CategoryCreateRequest request) {
        categoryService.createCategory(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long categoryId,
                                               @RequestBody @Valid CategoryUpdateRequest request) {
        categoryService.updateCategory(categoryId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> findCategories() {
        List<CategoryResponse> response = categoryService.findCategories();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }
    
}
