package com.bikkadIt.electronic.store.services;

import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;

public interface CategoryService {

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto, String categoryId);

    void delete(String categoryId);

    PageableResponse<CategoryDto> getAll();

    CategoryDto get(String categoryId);



}
