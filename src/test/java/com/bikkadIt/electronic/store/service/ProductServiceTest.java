package com.bikkadIt.electronic.store.service;

import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.repositories.ProductRepository;
import com.bikkadIt.electronic.store.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ModelMapper mapper;

    Product product;

    Category category;

    @BeforeEach
    void init() {

        category = Category.builder()
                .title("Electronics Product")
                .description("This is electronics Category")
                .coverImage("abc.png")
                .build();

        product = Product.builder().name("Mobile").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(category).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

    }

    @Test
    public void createProductData() {

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = productService.create(mapper.map(product, ProductDto.class));
        System.out.println(productDto.getName());
        Assertions.assertNotNull(productDto);
        Assertions.assertEquals("Mobile", productDto.getName());

    }

    @Test
    public void updateProductData() {

        String productId = "abcd";

        ProductDto dto = ProductDto.builder().name("Laptop").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(mapper.map(category, CategoryDto.class)).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto update = productService.update(dto, productId);
        System.out.println(update.getName());
        Assertions.assertEquals(dto.getName(), update.getName(), "Product name is not equal");

    }

    @Test
    public void deleteProduct() {
        String productId = "asedrf";
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        productService.delete(productId);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);

    }

    @Test
    public void getAllProduct() {
        Product product1 = product = Product.builder().name("Mobile").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(category).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        Product product2 = product = Product.builder().name("Mobile").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(category).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();
        List<Product> productList = Arrays.asList(product, product1, product2);

        Page<Product> products = new PageImpl<>(productList);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(products);
        Sort sort = Sort.by("name").ascending();

        PageRequest request = PageRequest.of(1, 2, sort);

        PageableResponse<ProductDto> allProduct = productService.getAll(1, 2, "name", "asc");

        Assertions.assertEquals(3, allProduct.getContent().size());

    }

    @Test
    public void getProductByIdTest() {
        String productId = "abcdefgh";
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        ProductDto productDto = productService.get(productId);
        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(product.getName(), productDto.getName(), "Product name not matched");
    }

    @Test
    public void getAllLiveProductTest() {
        Product product1 = product = Product.builder().name("Mobile").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(category).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        Product product2 = product = Product.builder().name("Mobile").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(category).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();
        List<Product> productList = Arrays.asList(product, product1, product2);

        Page<Product> products = new PageImpl<>(productList);
        Mockito.when(productRepository.findByLiveTrue((Pageable) Mockito.any())).thenReturn(products);
        Sort sort = Sort.by("name").ascending();

        PageRequest request = PageRequest.of(1, 2, sort);
        PageableResponse<ProductDto> allProduct = productService.getAllLive(1, 2, "name", "asc");
        Assertions.assertEquals(3, allProduct.getContent().size());
    }

//        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//        Page<Product> page = productRepository.findByLiveTrue(pageable);
//        logger.info("Completed the dao call for the get all live product data ");
//        return Helper.getPageableResponse(page, ProductDto.class);

}


