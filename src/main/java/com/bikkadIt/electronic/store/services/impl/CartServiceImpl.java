package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.constant.AppConstants;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
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
        logger.info("Initiating dao call to add item into cart with userId:{}", userId);
        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if (quantity <= 0) {
            throw new BadApiRequestException(AppConstants.INVALID_QUANTITY);
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));

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
                item.setQuantity(quantity + item.getQuantity()); // change
                item.setTotalPrice(quantity * product.getPrice() + item.getTotalPrice());
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
        logger.info("Completed dao call to add item into cart with userId:{}", userId);
        return mapper.map(updateCart, CartDto.class);
    }

    @Override
    public void removeItemFormCart(String userId, int cartItem) {
        logger.info("Initiating dao call to remove  item from cart with userId:{}", userId);
        CartItem item = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CART_NOT_FOUND));
        logger.info("Completed dao call to remove  item from cart with userId:{}", userId);
        cartItemRepository.delete(item);

    }

    @Override
    public void clearCart(String userId) {
        logger.info("Initiating dao call to clear  cart with userId:{}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CART_NOT_FOUND));
        cart.getItems().clear();
        logger.info("Completed dao call to clear cart with userId:{}", userId);
        cartRepository.save(cart);

    }

    @Override
    public CartDto getCartByUser(String userId) {
        logger.info("Initiating dao call to get  cart with userId:{}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CART_NOT_FOUND));
        logger.info("Completed dao call to clear  cart with userId:{}", userId);
        return mapper.map(cart, CartDto.class);
    }
}
