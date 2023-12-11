package com.bikkadIt.electronic.store.services;

import com.bikkadIt.electronic.store.dtos.AddItemToCartRequest;
import com.bikkadIt.electronic.store.dtos.CartDto;

public interface CartService {

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    void removeItemFormCart(String userId, int cartItem);

    void clearCart(String userId);
}
