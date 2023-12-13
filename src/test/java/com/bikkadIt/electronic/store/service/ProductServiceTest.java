package com.bikkadIt.electronic.store.service;

import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.ProductDto;
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

import java.util.Date;

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


}


