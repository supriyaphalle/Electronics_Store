package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadIt.electronic.store.repositories.ProductRepository;
import com.bikkadIt.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public ProductDto create(ProductDto productDto) {
        Product product= mapper.map(productDto,Product.class);
        Product save = productRepository.save(product);
        return mapper.map(save,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {


        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given Id"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setDiscountPrice(productDto.getDiscountPrice());
        product.setStock(productDto.isStock());

        Product save = productRepository.save(product);
        return mapper.map(save,ProductDto.class);

    }

    @Override
    public void delete(String productId) {

    }

    @Override
    public ProductDto get(String productId) {
        return null;
    }

    @Override
    public List<ProductDto> getAll() {
        return null;
    }

    @Override
    public List<ProductDto> getAllLive() {
        return null;
    }

    @Override
    public List<ProductDto> searchByTitle(String subTitle) {
        return null;
    }
}
