package ru.miaat.Ecommerce.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.miaat.Ecommerce.Dto.CategoryDto;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Service.interf.CategoryService;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping("/update/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDto));
    }

    @DeleteMapping("/delete/{catId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteCategory(@PathVariable Long catId) {
        return ResponseEntity.ok(categoryService.deleteCategory(catId));
    }

    @GetMapping("/get-by-id/{catId}")
    public ResponseEntity<Response> getCategoryById(@PathVariable Long catId) {
        return ResponseEntity.ok(categoryService.getCategoryById(catId));
    }
}
