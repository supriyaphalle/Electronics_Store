package com.bikkadIt.electronic.store.repositories;

import com.bikkadIt.electronic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
