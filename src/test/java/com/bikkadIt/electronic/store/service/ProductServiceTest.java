package com.bikkadIt.electronic.store.service;

import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.repositories.CategoryRepository;
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

    @MockBean
    CategoryRepository categoryRepository;

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


    @Test
    public void searchProductTest() {
        Product product1 = product = Product.builder().name("Mobile").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(category).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        Product product2 = product = Product.builder().name("Mobile").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(category).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();
        List<Product> productList = Arrays.asList(product, product1, product2);

        Page<Product> products = new PageImpl<>(productList);

        Mockito.when(productRepository.findByNameContaining(Mockito.anyString(), Mockito.any())).thenReturn(products);
        PageableResponse<ProductDto> searchByTitle = productService.searchByTitle("mobile", 1, 2, "name", "asc");
        Assertions.assertEquals(3, searchByTitle.getContent().size());

    }


    @Test
    public void createWithCategoryProduct() {

        String categoryId = "abcd";
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto productDto = productService.createWithCategory(mapper.map(product, ProductDto.class), categoryId);

        Assertions.assertEquals(productDto.getName(), product.getName(), "Name not match");

    }

    @Test
    public void updateCategoryTest() {
        String categoryId = "abcd";
        String productId = "pqrs";

        ProductDto dto = ProductDto.builder().name("Laptop").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(mapper.map(category, CategoryDto.class)).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(mapper.map(dto,Product.class)));
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto productDto1 = productService.updateCategory(productId, categoryId);
        Assertions.assertEquals(category.getTitle(), productDto1.getCategory().getTitle(), "category title not match");

    }


}


