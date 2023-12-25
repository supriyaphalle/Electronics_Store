package com.bikkadIt.electronic.store.controller;

import com.bikkadIt.electronic.store.dtos.CartDto;
import com.bikkadIt.electronic.store.entities.*;
import com.bikkadIt.electronic.store.services.CartService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class
CartControllerTest {

    Cart cart;

    User user;
    @MockBean
    CartService cartService;

    @Autowired
    ModelMapper mapper;
    List<CartItem> items = new ArrayList<>();
    Category category;
    Product p;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() {

        category = Category.builder()
                .title("Electronics Product")
                .description("This is electronics Category")
                .coverImage("abc.png")
                .build();

        p = Product.builder().name("Mobile").price(15000).stock(true).live(true)
                .productImage("pqr.png").quantity(13).category(category).discountPrice(14000)
                .addedDate(new Date()).description("This is Product service test").build();
        items.add(new CartItem(1, p, 2, 30, cart));

        user = User.builder()
                .name("Supriya")
                .email("supriya@gmail.com")
                .about("This  is test Controller")
                .gender("Female")
                .imageName("abc.png")
                .password("Supriya@1234")
                .build();

        cart = Cart.builder().cartId("abcd").createdAt(new Date()).user(user).items(items).build();
    }


    @Test
    public void addCartTest() throws Exception {
        CartDto cartDto = mapper.map(cart, CartDto.class);
        String userID = "qwert";
        Mockito.when(cartService.addItemToCart(Mockito.anyString(), Mockito.any())).thenReturn(cartDto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/carts/" + userID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(cart))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    private String convertObjectToJsonString(Cart cart) {
        try {
            return new ObjectMapper().writeValueAsString(cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void CartRemoveTest() throws Exception {
        String id = "abcd";
        int iid = 3;
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/carts/" + id + "/items/" + iid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    public void ClearCartTest() throws Exception {
        String userId = "abcd";
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/carts/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCartTest() throws Exception {
        String userId = "abcd";
        Mockito.when(cartService.getCartByUser(Mockito.anyString())).thenReturn(mapper.map(cart, CartDto.class));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/carts/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(cart))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());

    }
}