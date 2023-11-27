package com.bikkadIt.electronic.store.controllers;

import com.bikkadIt.electronic.store.constant.AppConstants;
import com.bikkadIt.electronic.store.dtos.ApiResponseMessage;
import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    /**
     * @param categoryDto
     * @return http status for save data
     * @author SUPRIYA
     * @apiNote To save category data in  database
     * @since V 1.0
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createData(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Entering Request to create category Data");
        CategoryDto category = categoryService.create(categoryDto);
        logger.info("Completed Request to create category data");
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * @param categoryDto , categoryId
     * @return http status for save data
     * @author SUPRIYA
     * @apiNote To update category data in  database
     * @since V 1.0
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateData(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        logger.info("Entering Request to update category Data for id:{}", categoryId);
        CategoryDto update = categoryService.update(categoryDto, categoryId);
        logger.info("Entering Request to update category Data for id:{}", categoryId);
        return new ResponseEntity<CategoryDto>(update, HttpStatus.OK);
    }

    /**
     * @return http status for delete data
     * @author SUPRIYA
     * @apiNote To delete category data in  database
     * @since V 1.0
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteData(@PathVariable String categoryId) {
        logger.info("Entering request to delete category data for id:{}", categoryId);
        categoryService.delete(categoryId);
        ApiResponseMessage message = new ApiResponseMessage(AppConstants.DELETE_CATEGORY_RESPONSE, true, HttpStatus.OK);
        logger.info("Completed request to delete category data for id:{}", categoryId);
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    /**
     * @param
     * @return get all category data
     * @author SUPRIYA
     * @apiNote To get category data from  database
     * @since V 1.0
     */
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllData(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering request to delete category data ");
        PageableResponse<CategoryDto> allList = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request to delete category data ");
        return new ResponseEntity<>(allList, HttpStatus.OK);
    }

    /**
     * @return http status for update data
     * @Param categoryID
     * @author SUPRIYA
     * @apiNote To get category data from database
     * @since V 1.0
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryDataById(@PathVariable String categoryId) {
        logger.info("Entering request to get category data for id:{}", categoryId);
        CategoryDto categoryDto = categoryService.get(categoryId);
        logger.info("Completed request to get category data for id:{}", categoryId);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }


}
