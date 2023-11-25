package com.bikkadIt.electronic.store.controllers;

import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping
    public ResponseEntity<CategoryDto> createData(CategoryDto categoryDto) {
        logger.info("Entering Request to create category Data");
        CategoryDto category = categoryService.create(categoryDto);
        logger.info("Completed Request to create category data");
        return new ResponseEntity(category, HttpStatus.CREATED);
    }

}
