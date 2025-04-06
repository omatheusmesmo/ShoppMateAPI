package com.omatheusmesmo.shoppmate.category.service;

import com.omatheusmesmo.shoppmate.category.entity.Category;
import com.omatheusmesmo.shoppmate.category.repository.CategoryRepository;
import com.omatheusmesmo.shoppmate.shared.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AuditService auditService;

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
    //TODO improve it
    public void removeCategory(Category category){
        auditService.softDelete(category);
        saveCategory(category);
    }

    public void isCategoryValid(Category category) {
        category.checkName();
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
