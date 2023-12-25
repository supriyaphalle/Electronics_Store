package com.bikkadIt.electronic.store.service;

import com.bikkadIt.electronic.store.dtos.AddItemToCartRequest;
import com.bikkadIt.electronic.store.dtos.CartDto;
import com.bikkadIt.electronic.store.dtos.CartItemDto;
import com.bikkadIt.electronic.store.entities.*;
import com.bikkadIt.electronic.store.repositories.CartItemRepository;
import com.bikkadIt.electronic.store.repositories.CartRepository;
import com.bikkadIt.electronic.store.repositories.ProductRepository;
import com.bikkadIt.electronic.store.repositories.UserRepository;
import com.bikkadIt.electronic.store.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class CartServiceTest {

    @MockBean
    CartRepository cartRepository;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    CartItemRepository cartItemRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    CartService cartService;

    @Autowired
    ModelMapper mapper;

    Cart cart;

    User user;
    List<CartItem> items = new ArrayList<>();
    Category category;
    Product p;

    @BeforeEach
    void init() {
        category = Category.builder()
                .title("Electronics Product")
                .description("This is electronics Category")
                .coverImage("abc.png")
                .build();

        p = Product.builder().productId("ssss").name("Mobile").price(15000).stock(true).live(true)
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
    public void addCartTest() {

        AddItemToCartRequest request = new AddItemToCartRequest(3, p.getProductId());

        items.add(new CartItem(1, p, 5, 30, cart));
        List<CartItemDto> dtos = items.stream().map(i -> mapper.map(i, CartItemDto.class)).collect(Collectors.toList());

        CartDto updatedCart = CartDto.builder().cartId(cart.getCartId()).items(dtos).createdAt(new Date()).user(user).build();
        Cart cart1 = Cart.builder().cartId(cart.getCartId()).items(items).createdAt(new Date()).user(user).build();

        String userId = "aaaa";
        AddItemToCartRequest cartRequest = new AddItemToCartRequest(2, "abcd");
        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(p));
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        Mockito.when(cartRepository.findByUser(Mockito.any())).thenReturn(Optional.ofNullable(cart));

        Mockito.when(cartRepository.save(Mockito.any())).thenReturn(cart1);

        CartDto cartDto = cartService.addItemToCart(userId, request);
        System.out.println(cartDto.getItems());
        Assertions.assertEquals(2, cartDto.getItems().size());
    }

    @Test
    void RemoveCartItemTest() {
        String userId = "abcd";
        int cartItem = 1;

        Mockito.when(cartItemRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(items.get(0)));
        cartService.removeItemFormCart(userId, cartItem);
        Mockito.verify(cartItemRepository, Mockito.times(1)).delete(items.get(0));

    }

    @Test
    public void removeCartItem() {
        String userId = "abcd";

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        Mockito.when(cartRepository.findByUser(Mockito.any())).thenReturn(Optional.ofNullable(cart));
        cartService.clearCart(userId);
        Mockito.verify(cartRepository, Mockito.times(1)).save(cart);
    }

    @Test
    public void getCartByUserTest() {
        String userId = "abcd";
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        Mockito.when(cartRepository.findByUser(Mockito.any())).thenReturn(Optional.ofNullable(cart));
        CartDto cartDto = cartService.getCartByUser(userId);
        Assertions.assertNotNull(cartDto);
        Assertions.assertEquals(cartDto.getUser().getName(), "Supriya", "User name not matched");
        Assertions.assertEquals(cartDto.getCartId(),cart.getCartId(),"Cart id not matched");


    }


}
