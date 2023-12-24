package com.bikkadIt.electronic.store.controller;

import com.bikkadIt.electronic.store.dtos.CreateOrderRequest;
import com.bikkadIt.electronic.store.dtos.OrderDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.entities.*;
import com.bikkadIt.electronic.store.services.OrderService;
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
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @MockBean
    OrderService orderService;

    @Autowired
    ModelMapper mapper;

    @Autowired
    MockMvc mockMvc;

    Order order;

    User user;
    List<OrderItem> orderItems = new ArrayList<>();
    Category category;
    Product p;
    Cart cart;
    List<CartItem> items = new ArrayList<>();

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


        user = User.builder().userId("aaaa")
                .name("Supriya")
                .email("supriya@gmail.com")
                .about("This  is test Controller")
                .gender("Female")
                .imageName("abc.png")
                .password("Supriya@1234")
                .build();

        orderItems.add(new OrderItem(2, 5, 25, p, order));

        order = Order.builder().orderId("abcd").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Pune").billingPhone("987456321").orderDate(new Date())
                .user(user).orderItems(orderItems).build();
        items.add(new CartItem(1, p, 2, 30, cart));
        cart = Cart.builder().cartId("abcd").createdAt(new Date()).user(user).items(items).build();
    }

    @Test
    public void createOrderTest() throws Exception {

        CreateOrderRequest orderRequest = CreateOrderRequest.builder().cartId("abcd")
                .userId(user.getUserId())
                .orderStatus("PENDING")
                .paymentStatus("NOTPAID")
                .billingAddress("Pune")
                .billingPhone("9874569852")
                .billingName("Supriya").build();


        Mockito.when(orderService.createOrder(Mockito.any())).thenReturn(mapper.map(order, OrderDto.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(orderRequest))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    private String convertObjectToJsonString(CreateOrderRequest request) {
        try {
            return new ObjectMapper().writeValueAsString(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void removeOrderTest() throws Exception {
        String orderId = "abcd";
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getOrderOfUser() throws Exception {
        String userId = "abcd";

        Order order2 = Order.builder().orderId("poer").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Pune").billingPhone("987456321").orderDate(new Date())
                .user(user).orderItems(orderItems).build();

        Order order3 = Order.builder().orderId("stur").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Pune").billingPhone("987456321").orderDate(new Date())
                .user(user).orderItems(orderItems).build();

        List<Order> list = new ArrayList<>();
        list.add(order);
        list.add(order2);
        list.add(order3);

        List<OrderDto> dtos = list.stream().map(i -> mapper.map(i, OrderDto.class)).collect(Collectors.toList());
        Mockito.when(orderService.getOrderOfUser(Mockito.anyString())).thenReturn(dtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/user/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllOrders() throws Exception {
        Order order2 = Order.builder().orderId("poer").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Pune").billingPhone("987456321").orderDate(new Date())
                .user(user).orderItems(orderItems).build();

        Order order3 = Order.builder().orderId("stur").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Pune").billingPhone("987456321").orderDate(new Date())
                .user(user).orderItems(orderItems).build();

        List<Order> list = new ArrayList<>();
        list.add(order);
        list.add(order2);
        list.add(order3);

        List<OrderDto> dtos = list.stream().map(i -> mapper.map(i, OrderDto.class)).collect(Collectors.toList());
        PageableResponse<OrderDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(dtos);

        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(20);
        pageableResponse.setTotalElements(100);

        Mockito.when(orderService.getOrders(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk());


    }


}
