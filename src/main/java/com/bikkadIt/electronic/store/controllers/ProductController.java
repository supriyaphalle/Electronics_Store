package com.bikkadIt.electronic.store.controllers;

import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping("/")
    public ResponseEntity<ProductDto> createData(@Valid @RequestBody ProductDto productDto){
        ProductDto dto = productService.create(productDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
