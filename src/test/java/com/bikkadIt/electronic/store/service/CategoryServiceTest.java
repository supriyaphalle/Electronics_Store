package com.bikkadIt.electronic.store.service;


import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.repositories.CategoryRepository;
import com.bikkadIt.electronic.store.services.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    CategoryRepository repository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ModelMapper mapper;
    Category category;

    @BeforeEach
    void init() {
        category = Category.builder()
                .title("Electronics Product")
                .description("This is electronics Category")
                .coverImage("abc.png")
                .build();
    }

    @Test
    public void createCategoryTest() {

        Mockito.when(repository.save(Mockito.any())).thenReturn(category);
        CategoryDto dto = categoryService.createCategory(mapper.map(category, CategoryDto.class));
        Assertions.assertNotNull(dto);
        Assertions.assertEquals("Electronics Product", dto.getTitle(), "Title not matched");

    }


}
