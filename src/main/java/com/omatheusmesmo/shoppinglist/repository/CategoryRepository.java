package com.omatheusmesmo.shoppinglist.repository;

import com.omatheusmesmo.shoppinglist.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
