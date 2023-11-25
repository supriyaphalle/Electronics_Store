package com.bikkadIt.electronic.store.controllers;

import com.bikkadIt.electronic.store.constant.AppConstants;
import com.bikkadIt.electronic.store.dtos.ApiResponseMessage;
import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping
    public ResponseEntity<CategoryDto> createData(@RequestBody CategoryDto categoryDto) {
        logger.info("Entering Request to create category Data");
        CategoryDto category = categoryService.create(categoryDto);
        logger.info("Completed Request to create category data");
        return new ResponseEntity(category, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateData(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        logger.info("Entering Request to update category Data for id:{}", categoryId);
        CategoryDto update = categoryService.update(categoryDto, categoryId);
        logger.info("Entering Request to update category Data for id:{}", categoryId);
        return  new ResponseEntity<CategoryDto>(update,HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage>deleteData(@PathVariable String categoryId){
        logger.info("Entering request to delete category data for id:{}",categoryId);
        categoryService.delete(categoryId);
        ApiResponseMessage message = new ApiResponseMessage(AppConstants.DELETE_RESPONSE, true, HttpStatus.OK);
        logger.info("Completed request to delete category data for id:{}",categoryId);
        return new ResponseEntity<ApiResponseMessage>(message,HttpStatus.OK);
    }


}
