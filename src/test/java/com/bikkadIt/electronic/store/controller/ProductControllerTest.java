package com.bikkadIt.electronic.store.controller;

import com.bikkadIt.electronic.store.controllers.ProductController;
import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
    ProductService productService;
    @Autowired
    ModelMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    private Product product;

    @InjectMocks
    private ProductController productController;


    private Category category;

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
    public void createProductData() throws Exception {
        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.create(Mockito.any())).thenReturn(productDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/product/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());


    }

    private String convertObjectToJsonString(Product product) {
        try {
            return new ObjectMapper().writeValueAsString(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Test
    public void updateProductTest() throws Exception {
        String productId = "abcd";
        ProductDto dto = this.mapper.map(product, ProductDto.class);

        Mockito.when(productService.update(Mockito.any(), Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());


    }

    @Test
    public void deleteProductTest() throws Exception {
        String productId = "abcd";

//        Mockito.verify(productService, Mockito.times(1)).delete(productId);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getPRProductByIdTest() throws Exception {
        String productId = "abcd";
        Mockito.when(productService.get(Mockito.anyString())).thenReturn(mapper.map(product, ProductDto.class));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
        ;


    }

    @Test
    public void getAllProduct() throws Exception {
        ProductDto product1 = ProductDto.builder().name("Mobile1").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(mapper.map(category, CategoryDto.class)).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        ProductDto product2 = ProductDto.builder().name("Mobile2").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(mapper.map(category, CategoryDto.class)).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        ProductDto product3 = ProductDto.builder().name("Mobile3").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(mapper.map(category, CategoryDto.class)).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(product1, product2, product3));

        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(20);
        pageableResponse.setTotalElements(100);


        Mockito.when(productService.getAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    public void getAllLiveProducts() throws Exception {

        ProductDto product1 = ProductDto.builder().name("Mobile1").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(mapper.map(category, CategoryDto.class)).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        ProductDto product2 = ProductDto.builder().name("Mobile2").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(mapper.map(category, CategoryDto.class)).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        ProductDto product3 = ProductDto.builder().name("Mobile3").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(mapper.map(category, CategoryDto.class)).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(product1, product2, product3));

        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(20);
        pageableResponse.setTotalElements(100);


        Mockito.when(productService.getAllLive(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/live")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void searchProductTest() throws Exception {
        ProductDto product1 = ProductDto.builder().name("Mobile1").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(mapper.map(category, CategoryDto.class)).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        ProductDto product2 = ProductDto.builder().name("Mobile2").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(mapper.map(category, CategoryDto.class)).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        ProductDto product3 = ProductDto.builder().name("Mobile3").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(mapper.map(category, CategoryDto.class)).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(product1, product2, product3));

        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(20);
        pageableResponse.setTotalElements(100);


        Mockito.when(productService.searchByTitle(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
        String keyword = "mobile";

        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/search/" + keyword)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk());

    }

}
