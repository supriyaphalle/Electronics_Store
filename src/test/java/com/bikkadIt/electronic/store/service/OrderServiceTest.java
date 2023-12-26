package com.bikkadIt.electronic.store.service;

import com.bikkadIt.electronic.store.dtos.CreateOrderRequest;
import com.bikkadIt.electronic.store.dtos.OrderDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.entities.*;
import com.bikkadIt.electronic.store.repositories.CartRepository;
import com.bikkadIt.electronic.store.repositories.OrderRepository;
import com.bikkadIt.electronic.store.repositories.UserRepository;
import com.bikkadIt.electronic.store.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.entities.Product;
import org.springframework.data.domain.*;

@SpringBootTest
public class OrderServiceTest {


    User user;

    Cart cart;
    List<CartItem> items = new ArrayList<>();

    List<OrderItem> orderItems = new ArrayList<>();
    Category category;
    Product p;
    Order order;

    @Autowired
    OrderService orderService;

    @MockBean
    UserRepository userRepository;


    @MockBean
    OrderRepository orderRepository;
    @MockBean
    CartRepository cartRepository;
    @Autowired
    ModelMapper mapper;

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
    public void createOrderTest() {

        CreateOrderRequest orderRequest = CreateOrderRequest.builder().cartId("abcd")
                .userId(user.getUserId())
                .orderStatus("PENDING")
                .paymentStatus("NOTPAID")
                .billingAddress("Pune")
                .billingPhone("9874569852")
                .billingName("Supriya").build();


        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        Mockito.when(cartRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(cart));
        Mockito.when(cartRepository.save(Mockito.any())).thenReturn(cart);

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);

        OrderDto order1 = this.orderService.createOrder(orderRequest);
        System.out.println(order1);
        Assertions.assertEquals(order.getBillingName(), order1.getBillingName(), "Billing name doesnot matches");

    }

    @Test
    public void removeOrderTest() {
        String orderId = "abcd";
        Mockito.when(orderRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(order));
        orderService.removeOrder(orderId);
        Mockito.verify(orderRepository, Mockito.times(1)).delete(order);

    }

    @Test
    public void getOrderOfUserTest() {
        Order order2 = Order.builder().orderId("abd").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Pune").billingPhone("987456321").orderDate(new Date())
                .user(user).orderItems(orderItems).build();

        Order order3 = Order.builder().orderId("acd").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Pune").billingPhone("987456321").orderDate(new Date())
                .user(user).orderItems(orderItems).build();


        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        orderList.add(order2);
        orderList.add(order3);
        String userId = "abcd";
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        Mockito.when(orderRepository.findByUser(Mockito.any())).thenReturn(orderList);

        List<OrderDto> orderOfUser = orderService.getOrderOfUser(userId);

        Assertions.assertEquals(orderList.size(), orderOfUser.size());

    }


    @Test
    void getAllTest() {
        Order order2 = Order.builder().orderId("abcd").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Pune").billingPhone("987456321").orderDate(new Date())
                .user(user).orderItems(orderItems).build();

        Order order3 = Order.builder().orderId("abcd").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Pune").billingPhone("987456321").orderDate(new Date())
                .user(user).orderItems(orderItems).build();
        List<Order> list = Arrays.asList(order, order2, order3);
        Page<Order> orderlist = new PageImpl<>(list);
        Mockito.when(orderRepository.findAll((Pageable) Mockito.any())).thenReturn(orderlist);
        Sort sort = Sort.by("name").ascending();

        PageRequest request = PageRequest.of(1, 2, sort);
        PageableResponse<OrderDto> allProduct = orderService.getOrders(1, 2, "name", "asc");
        Assertions.assertEquals(3, allProduct.getContent().size());


    }
}

