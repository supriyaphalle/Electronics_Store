package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadIt.electronic.store.repositories.CategoryRepository;
import com.bikkadIt.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceImpl implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        logger.info("Initiating the dao call for the save category data");
        Category category = modelMapper.map(categoryDto, Category.class);
        Category save = categoryRepository.save(category);
        logger.info("Completed the dao call for the save category data");
        return modelMapper.map(save, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        logger.info("Initiating the dao call for the update category data");
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found "));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());
        Category save = categoryRepository.save(category);
        logger.info("Completed the dao call for the update category data");
        return modelMapper.map(save, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        logger.info("Initiating the dao call for the delete category data");
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found "));
        categoryRepository.delete(category);
        logger.info("Completed the dao call for the delete category data");
    }

    @Override
    public PageableResponse<CategoryDto> getAll() {
        return null;
    }

    @Override
    public CategoryDto get(String categoryId) {
        return null;
    }
}
