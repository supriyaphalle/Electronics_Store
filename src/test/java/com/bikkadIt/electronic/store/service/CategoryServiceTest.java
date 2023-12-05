package com.bikkadIt.electronic.store.service;


import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.repositories.CategoryRepository;
import com.bikkadIt.electronic.store.services.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
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

    @Test
    public void updateCategoryTest() {

        String categoryId = "abcdxyz";
        CategoryDto dto = CategoryDto.builder()
                .title("Products")
                .description("This is category of Electronics products")
                .coverImage("abc.png")
                .build();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(repository.save(Mockito.any())).thenReturn(category);
        CategoryDto dto1 = categoryService.updateCategory(dto, categoryId);

        Assertions.assertNotNull(dto1);
        Assertions.assertEquals(dto.getTitle(), dto1.getTitle(), "Title not match");
    }

    @Test
    public void deleteCategoryTest() {

        String categoryId = "abcdefgh";
        Mockito.when(repository.findById(categoryId)).thenReturn(Optional.of(category));
        categoryService.deleteCategory(categoryId);

        Mockito.verify(repository, Mockito.times(1)).delete(category);

    }

    @Test
    public void getCategoryByIdTest() {
        String categoryId = "pqrstuv";
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(category.getTitle(), categoryDto.getTitle(), "Title not matched");

    }

    @Test
    public void getAllCategoryTest() {
        Category category1 = Category.builder()
                .title("Electronics Product")
                .description("This is electronics Category")
                .coverImage("abc.png")
                .build();
        Category category2 = Category.builder()
                .title("Electronics Product")
                .description("This is electronics Category")
                .coverImage("abc.png")
                .build();

        List<Category> list = Arrays.asList(category, category1, category2);

        Page page = new PageImpl<>(list);
        Mockito.when(repository.findAll((Pageable) Mockito.any())).thenReturn(page);

        Sort sort = Sort.by("title").ascending();
        PageRequest request = PageRequest.of(1, 2, sort);

        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(1, 2, "title", "asc");

        Assertions.assertEquals(3,allCategory.getContent().size());

    }

}
