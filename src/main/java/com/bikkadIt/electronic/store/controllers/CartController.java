package com.bikkadIt.electronic.store.controllers;

import com.bikkadIt.electronic.store.constant.AppConstants;
import com.bikkadIt.electronic.store.dtos.AddItemToCartRequest;
import com.bikkadIt.electronic.store.dtos.ApiResponseMessage;
import com.bikkadIt.electronic.store.dtos.CartDto;
import com.bikkadIt.electronic.store.services.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    Logger logger = LoggerFactory.getLogger(CartService.class);
    @Autowired
    private CartService cartService;

    /**
     * @return http status for added cart data
     * @Param userID, AddItemToCartRequest
     * @author SUPRIYA
     * @apiNote To add item yo cart
     * @since V 1.0
     */
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {

        logger.info("Entering Request to add item to cart for id:{}", userId);
        CartDto cartDto = cartService.addItemToCart(userId, request);
        logger.info("Completed Request to add item to cart for id:{}", userId);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    /**
     * @return ApiResponse to remove cart data
     * @Param userID, itemId
     * @author SUPRIYA
     * @apiNote remove item from cart
     * @since V 1.0
     */
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(
            @PathVariable String userId, @PathVariable int itemId
    ) {
        logger.info("Entering Request to remove item from cart for id:{}", userId);
        cartService.removeItemFormCart(userId, itemId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message(AppConstants.REMOVE_CART)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        logger.info("Completed Request to remove item from cart for id:{}", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @return ApiResponse to clear cart
     * @Param userID
     * @author SUPRIYA
     * @apiNote clear  cart
     * @since V 1.0
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId) {
        logger.info("Entering Request to clear cart for id:{}", userId);
        cartService.clearCart(userId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message(AppConstants.CLEAR_CART)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        logger.info("Completed Request to clear cart for id:{}", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @return CartDto
     * @Param userID
     * @author SUPRIYA
     * @apiNote get all cart data
     * @since V 1.0
     */
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        logger.info("Entering Request to get cart for id:{}", userId);
        CartDto dto = cartService.getCartByUser(userId);
        logger.info("Completed Request to get cart for id:{}", userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}
