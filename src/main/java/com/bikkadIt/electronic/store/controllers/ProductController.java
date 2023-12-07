package com.bikkadIt.electronic.store.controllers;

import com.bikkadIt.electronic.store.constant.AppConstants;
import com.bikkadIt.electronic.store.dtos.ApiResponseMessage;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    /**
     * @return http status for created data
     * @Param productDto
     * @author SUPRIYA
     * @apiNote To create product data in database
     * @since V 1.0
     */
    @PostMapping("/")
    public ResponseEntity<ProductDto> createData(@Valid @RequestBody ProductDto productDto) {
        logger.info("Entering Request to create product Data");
        ProductDto dto = productService.create(productDto);
        logger.info("Completed Request to create product data");
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    /**
     * @return http status for update data
     * @Param productId, productDto
     * @author SUPRIYA
     * @apiNote To update product data in database
     * @since V 1.0
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId, @Valid @RequestBody ProductDto productDto) {
        logger.info("Entering Request to update product Data for id:{}", productId);
        ProductDto update = productService.update(productDto, productId);
        logger.info("Completed Request to update product Data for id:{}", productId);
        return new ResponseEntity<>(update, HttpStatus.OK);

    }

    /**
     * @return api response for delete data
     * @Param productId
     * @author SUPRIYA
     * @apiNote To delete product data in database
     * @since V 1.0
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable String productId) {
        logger.info("Entering Request to delete product Data for id:{}", productId);
        productService.delete(productId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("Product Deleted successfully!!").success(true).status(HttpStatus.OK).build();
        logger.info("Completed Request to delete product Data for id:{}", productId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * @return product_dto
     * @Param productId
     * @author SUPRIYA
     * @apiNote To get single  product data from database
     * @since V 1.0
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        logger.info("Entering request to get product data for id:{}", productId);
        ProductDto dto = productService.get(productId);
        logger.info("Completed request to get product data for id:{}", productId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * @return list of all product_dto
     * @Param
     * @author SUPRIYA
     * @apiNote To get all product data from database
     * @since V 1.0
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering request to get all product data ");
        PageableResponse<ProductDto> all = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request to get all product data ");
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    /**
     * @return list of all live product_dto
     * @Param
     * @author SUPRIYA
     * @apiNote To get all live product data from database
     * @since V 1.0
     */
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering request to get all live product data ");
        PageableResponse<ProductDto> all = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request to get all live  product data ");
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    /**
     * @return list of all searched product_dto
     * @Param
     * @author SUPRIYA
     * @apiNote To search product data from database
     * @since V 1.0
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String keyword,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {

        logger.info("Entering request to search  product data by keyword:{}", keyword);
        PageableResponse<ProductDto> all = productService.searchByTitle(keyword, pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request to search  product data by keyword:{}", keyword);
        return new ResponseEntity<>(all, HttpStatus.OK);

    }


}
