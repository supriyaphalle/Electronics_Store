package com.bikkadIt.electronic.store.controller;

import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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



}
