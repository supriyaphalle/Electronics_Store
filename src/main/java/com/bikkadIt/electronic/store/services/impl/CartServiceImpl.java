package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.dtos.AddItemToCartRequest;
import com.bikkadIt.electronic.store.dtos.CartDto;
import com.bikkadIt.electronic.store.entities.Cart;
import com.bikkadIt.electronic.store.entities.CartItem;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.entities.User;
import com.bikkadIt.electronic.store.exceptions.BadApiRequestException;
import com.bikkadIt.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadIt.electronic.store.repositories.CartItemRepository;
import com.bikkadIt.electronic.store.repositories.CartRepository;
import com.bikkadIt.electronic.store.repositories.ProductRepository;
import com.bikkadIt.electronic.store.repositories.UserRepository;
import com.bikkadIt.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if (quantity <= 0) {
            throw new BadApiRequestException("Requested quantity is not valid");
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found in database!!"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found in database!!"));

        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        List<CartItem> items = cart.getItems();

        List<CartItem> updatedITems = items.stream().map(item -> {

            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedITems);

        if (!updated.get()) {
            CartItem cartItem = CartItem.builder().quantity(quantity).totalPrice(quantity * product.getPrice())
                    .cart(cart).product(product).build();

            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart updateCart = cartRepository.save(cart);

        return mapper.map(updateCart, CartDto.class);
    }

    @Override
    public void removeItemFormCart(String userId, int cartItem) {

        CartItem item = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found in database!!"));
        cartItemRepository.delete(item);

    }

    @Override
    public void clearCart(String userId) {


    }

    @Override
    public CartDto getCartByUser(String userId) {
        return null;
    }
}
