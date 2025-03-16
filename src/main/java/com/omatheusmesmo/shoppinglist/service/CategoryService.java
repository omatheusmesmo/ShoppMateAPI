package com.omatheusmesmo.shoppinglist.service;

import com.omatheusmesmo.shoppinglist.entity.Category;
import com.omatheusmesmo.shoppinglist.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public void saveCategory(Category category){
        isCategoryValid(category);
        categoryRepository.save(category);
    }

    public Optional<Category> findCategoryById(Long id){
        return categoryRepository.findById(id);
    }

    public Optional<Category> findCategoryByName(String name){
        return categoryRepository.findByName(name);
    }

    public void removeCategory(Category category){
        category.setDeleted(true);
        saveCategory(category);
    }

    public void isCategoryValid(Category category) {
        checkName(category.getName());
    }

    private void checkName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("The unit name cannot be null!");
        } else if (name.isBlank()) {
            throw new IllegalArgumentException("Enter a valid unit name!");
        }
    }
}
