package com.bikkadIt.electronic.store.controller;

import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.services.CategoryService;
import com.bikkadIt.electronic.store.services.FileService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.io.IOException;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @MockBean
    CategoryService categoryService;

    @MockBean
    FileService fileService;
    Category category;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        category = Category.builder()
                .title("TestCase")
                .description("This is test case ")
                .coverImage("pqrs.png")
                .build();
    }

    @Test
    public void createCategoryTest() throws Exception {
        CategoryDto dto = mapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());


    }

    private String convertObjectToJsonString(Category category) {
        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void updateCategoryTest() throws Exception {
        String categoryId = "abcd";
        CategoryDto dto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/category/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }



    @Test
    public void getAllCategoryTest() throws Exception {

        CategoryDto dto1 = CategoryDto.builder().title("TestCase").description("This is test case ").coverImage("pqrs.png").build();
        CategoryDto dto2 = CategoryDto.builder().title("TestCase").description("This is test case ").coverImage("pqrs.png").build();
        CategoryDto dto3 = CategoryDto.builder().title("TestCase").description("This is test case ").coverImage("pqrs.png").build();
        CategoryDto dto4 = CategoryDto.builder().title("TestCase").description("This is test case ").coverImage("pqrs.png").build();

        PageableResponse<CategoryDto> response = new PageableResponse<>();
        response.setContent(Arrays.asList(dto1, dto2, dto3, dto4));
        response.setLastPage(false);
        response.setTotalElements(100);
        response.setPageSize(10);
        response.setPageNumber(20);

        Mockito.when(categoryService.getAllCategory(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getCategoryByCategoryId() throws Exception {
        String categoryId = "abcd";
        CategoryDto dto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.getCategory(Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/category/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

}