package com.omatheusmesmo.shoppmate.controller;

import com.omatheusmesmo.shoppmate.entity.Category;
import com.omatheusmesmo.shoppmate.service.CategoryService;
import com.omatheusmesmo.shoppmate.utils.HttpResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Return all categories")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategorys() {
        try {
            List<Category> categories = categoryService.findAll();
            return HttpResponseUtil.ok(categories);
        } catch (Exception e) {
            return HttpResponseUtil.internalServerError();
        }
    }

    @Operation(summary = "Add a new category")
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        try {
            categoryService.saveCategory(category);
            return HttpResponseUtil.created(category);
        } catch (IllegalArgumentException e) {
            return HttpResponseUtil.badRequest(category);
        }
    }

    @Operation(summary = "Delete a category by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            Optional<Category> category = categoryService.findCategoryById(id);
            assert category.orElse(null) != null;
            categoryService.removeCategory(category.orElse(null));
            return HttpResponseUtil.noContent();
        } catch (NoSuchElementException exception) {
            return HttpResponseUtil.notFound();
        }
    }

    @Operation(summary = "Update a category")
    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        try {
            categoryService.saveCategory(category);
            return HttpResponseUtil.ok(category);
        } catch (NoSuchElementException noSuchElementException) {
            return HttpResponseUtil.notFound();
        } catch (IllegalArgumentException illegalArgumentException) {
            return HttpResponseUtil.badRequest(category);
        }
    }
}
