package com.nimap.category_product.controller;
import com.nimap.category_product.entity.Category;
import com.nimap.category_product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // GET all categories with pagination
    @GetMapping
    public ResponseEntity<Page<Category>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Category> categories = categoryService.getAllCategories(page, size);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // POST create new category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category created = categoryService.createCategory(category);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // PUT update category by ID
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id, 
            @RequestBody Category categoryDetails) {
        Category updated = categoryService.updateCategory(id, categoryDetails);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // DELETE category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

