package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.constant.AppConstants;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.dtos.ProductDto;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.entities.Product;
import com.bikkadIt.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadIt.electronic.store.helper.Helper;
import com.bikkadIt.electronic.store.repositories.CategoryRepository;
import com.bikkadIt.electronic.store.repositories.ProductRepository;
import com.bikkadIt.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto create(ProductDto productDto) {
        logger.info("Initiating the dao call for the save product data");
        Product product = mapper.map(productDto, Product.class);
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setAddedDate(new Date());
        Product save = productRepository.save(product);
        logger.info("Completed the dao call for the save product data");
        return mapper.map(save, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {

        logger.info("Initiating the dao call for the update product data for id :{} ", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given Id"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setDiscountPrice(productDto.getDiscountPrice());
        product.setStock(productDto.isStock());
        product.setProductImage(productDto.getProductImage());

        Product save = productRepository.save(product);
        logger.info("Completed the dao call for the update product data for id :{} ", productId);
        return mapper.map(save, ProductDto.class);

    }

    @Override
    public void delete(String productId) {
        logger.info("Initiating the dao call for the delete product data for id :{} ", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
        logger.info("Completed the dao call for the update product data for id :{} ", productId);
    }

    @Override
    public ProductDto get(String productId) {
        logger.info("Initiating the dao call to get product data for id :{} ", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        logger.info("Completed the dao call to get product data for id :{} ", productId);
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating the dao call for the get product data ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);
        logger.info("Completed the dao call for the get product data ");
        return Helper.getPageableResponse(page, ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating the dao call for the get  all live product data ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        logger.info("Completed the dao call for the get all live product data ");
        return Helper.getPageableResponse(page, ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating the dao call to search product data  with keyword :{}", subTitle);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByNameContaining(subTitle, pageable);
        logger.info("Completed the dao call to search product data  with keyword :{}", subTitle);
        return Helper.getPageableResponse(page, ProductDto.class);

    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        logger.info("Initiating the dao call to create product data  with categoryId :{}", categoryId);

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        Product product = mapper.map(productDto, Product.class);
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);

        product.setAddedDate(new Date());
        product.setCategory(category);
        Product save = productRepository.save(product);

        ProductDto map = mapper.map(save, ProductDto.class);
        logger.info("Completed the dao call to create product data  with categoryId :{}", categoryId);
        return map;
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        logger.info("Initiating the dao call to update product data  with  categoryId and productId :{}", categoryId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        logger.info("Initiating the dao call to update product data  with  categoryId and productId :{}", categoryId);
        return mapper.map(saveProduct, ProductDto.class);
    }


}
