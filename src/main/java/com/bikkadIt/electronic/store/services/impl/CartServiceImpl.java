package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.dtos.AddItemToCartRequest;
import com.bikkadIt.electronic.store.dtos.CartDto;
import com.bikkadIt.electronic.store.services.CartService;

public class CartServiceImpl implements CartService {
    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        return null;
    }

    @Override
    public void removeItemFormCart(String userId, int cartItem) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
