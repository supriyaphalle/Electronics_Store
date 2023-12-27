package com.bikkadIt.electronic.store.controller;

import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.services.CategoryService;
import com.bikkadIt.electronic.store.services.FileService;
import com.bikkadIt.electronic.store.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @MockBean
    CategoryService categoryService;

    @MockBean
    FileService fileService;

    @MockBean
    ProductService productService;

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

    private String convertObjectToJsonString(Category category) {
        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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


    @Test
    public void deleteCategoryTest() throws Exception {
        String categoryId = "abcd";

        Mockito.doNothing().when(this.categoryService).deleteCategory(categoryId);

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete(("/category/" + categoryId))

        ).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void updateCategoryOfProductTest() throws Exception {
        Product product = Product.builder().name("Mobile").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(category).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();
        ProductDto productDto = mapper.map(product, ProductDto.class);

        String productId = "abcd";
        String categoryId = "ssss";
        Mockito.when(productService.updateCategory(productId, categoryId)).thenReturn(productDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/category/" + categoryId + "/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(jsonPath().exists());
    }

    @Test
    public void createProductWithCategoryTest() throws Exception {

        Product product = Product.builder().name("Mobile").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(category).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();
        ProductDto productDto = mapper.map(product, ProductDto.class);

        String productId = "abcd";
        String categoryId = "ssss";
        Mockito.when(productService.createWithCategory(productDto, categoryId)).thenReturn(productDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/category/" + categoryId + "/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


//    public void testGetUserByName() throws Exception {
//        String firstName = "Jack";
//        String lastName = "s";
//        this.userClientObject = client.createClient();
//        mockMvc.perform(get("/byName")
//                        .sessionAttr("userClientObject", this.userClientObject)
//                        .param("firstName", firstName)
//                        .param("lastName", lastName)
//                ).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$[0].id").exists())
//                .andExpect(jsonPath("$[0].fn").value("Marge"));
//    }


    @Test
    public void uploadImageTest() throws Exception {
        String categoryId = "ssss";

        MockMultipartFile categoryImage = new MockMultipartFile("file", "38b610cc-b181-4a4a-a87f-769d63bd7489.jpg",
                "image/jpg", "Some data".getBytes());


        MvcResult result = this.mockMvc.perform(multipart("/category/image/" + categoryId+"categoryImage?")

                        .file(categoryImage))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());

    }

    @Test
    public void getImageFromServer() throws Exception {

        MockMultipartFile imageFile = new MockMultipartFile("file","1.png","image/png","Some data".getBytes());

        MvcResult result = this.mockMvc.perform(multipart("/category/")
                        .file(imageFile)
                        .accept(MediaType.APPLICATION_JSON)

                )
                .andReturn();
        Assertions.assertEquals(200,result.getResponse().getStatus());

    }


//    @Test
//    void givenImageAsMultipart_shouldReturnImageViewURL() throws Exception {
//        MockMultipartFile imageFile = new MockMultipartFile("file","1.png",
//                "image/png","Some data".getBytes());
//        MvcResult result = this.mockMvc.perform(multipart("/admin/uploadimage")
//                        .file(imageFile))
//                .andReturn();
//        Assert.assertEquals(200,result.getResponse().getStatus());
//    }
//
//    @Test
//    void givenFileName_WhenFound_ReturnsFile() throws Exception {
//
//        String fileName="notebook.jpg";
//        String imagePath="//src//main//resources//Images//";
//        String fileBasePath = System.getProperty("user.dir")+imagePath;
//        Path path = Paths.get(fileBasePath + fileName);
//        Resource resource = new UrlResource(path.toUri());
//        Mockito.when(adminService.loadFile(any(), any())).thenReturn(resource);
//        MvcResult result = this.mockMvc.perform(get("/admin/downloadfile/fileName?fileName=imageName"))
//                .andReturn();
//        Assert.assertEquals(200,result.getResponse().getStatus());
//    }


//    @Test
//    public void postCategoryImageByID() throws Exception {
//        String categoryId = "qazxsw";
//        CategoryDto dto = mapper.map(category, CategoryDto.class);
//        String imageName = "fileName";
//        CategoryDto dto1 = mapper.map(category, CategoryDto.class);
//        dto1.setCoverImage(imageName);
//        MockMultipartFile file
//                = new MockMultipartFile(
//                "file",
//                "hello.txt",
//                MediaType.TEXT_PLAIN_VALUE,
//                "Hello, World!".getBytes()
//        );
//
//
//        Mockito.when(fileService.uploadFile(Mockito.any(), Mockito.any())).thenReturn(imageName);
//        Mockito.when(categoryService.getCategory(Mockito.anyString())).thenReturn(dto);
//        Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.anyString())).thenReturn(dto1);
//
//        this.mockMvc.perform(multipart("/category/image/" + categoryId)
//
//                        .file(file)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//
//                )
//                .andDo(print())
//                .andExpect(status().isCreated());
//
//
//    }


}