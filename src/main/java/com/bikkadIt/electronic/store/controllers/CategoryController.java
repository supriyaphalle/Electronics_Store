package com.bikkadIt.electronic.store.controllers;

import com.bikkadIt.electronic.store.constant.AppConstants;
import com.bikkadIt.electronic.store.dtos.*;
import com.bikkadIt.electronic.store.services.CategoryService;
import com.bikkadIt.electronic.store.services.FileService;
import com.bikkadIt.electronic.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    FileService fileService;
    Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Value("${category.profile.image.path}")
    private String imagePath;

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
        CategoryDto category = categoryService.createCategory(categoryDto);
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
        CategoryDto update = categoryService.updateCategory(categoryDto, categoryId);
        logger.info("Completed Request to update category Data for id:{}", categoryId);
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
        categoryService.deleteCategory(categoryId);
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
        logger.info("Entering request to get all category data ");
        PageableResponse<CategoryDto> allList = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request to get all category data ");
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
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        logger.info("Completed request to get category data for id:{}", categoryId);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    /**
     * @return Image response for upload image
     * @Param categoryID, image file
     * @author SUPRIYA
     * @apiNote To upload image file to database
     * @since V 1.0
     */
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("categoryImage") MultipartFile image, @PathVariable String categoryId) throws IOException {
        logger.info("Entering request to upload image with categoryId:{}", categoryId);
        String uploadImageFile = fileService.uploadFile(image, imagePath);
        CategoryDto dto = categoryService.getCategory(categoryId);
        dto.setCoverImage(uploadImageFile);
        CategoryDto update = categoryService.updateCategory(dto, categoryId);
        ImageResponse response = ImageResponse.builder().imageName(uploadImageFile).success(true).message(AppConstants.UPLOAD_RESPONSE).status(HttpStatus.CREATED).build();
        logger.info("Completed request to upload image with categoryId:{}", categoryId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * @return image from database
     * @Param categoryID
     * @author SUPRIYA
     * @apiNote To get image file from database
     * @since V 1.0
     */
    @GetMapping("/image/{categoryId}")
    public void getImageFromServer(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        logger.info("Entering request to get image file with categoryID:{}", categoryId);
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        InputStream resource = fileService.getResource(imagePath, categoryDto.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        logger.info("Completed request to get image file with categoryID:{}", categoryId);
        StreamUtils.copy(resource, response.getOutputStream());
    }


    /**
     * @return ProductDto
     * @Param categoryID , productDto
     * @author SUPRIYA
     * @apiNote To create Product with category ID
     * @since V 1.0
     */
    @PostMapping("/{categoryId}/product")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto

    ) {
        logger.info("Entering request to create product with categoryID:{}", categoryId);
        ProductDto productDto1 = productService.createWithCategory(productDto, categoryId);
        logger.info("Completed request to create product with categoryID:{}", categoryId);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }


    /**
     * @return ProductDto
     * @Param categoryID , productId
     * @author SUPRIYA
     * @apiNote To update category of Product data
     * @since V 1.0
     */
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(
            @PathVariable String categoryId, @PathVariable String productId
    ) {
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);

    }

}

