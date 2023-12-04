package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadIt.electronic.store.helper.Helper;
import com.bikkadIt.electronic.store.repositories.CategoryRepository;
import com.bikkadIt.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${category.profile.image.path}")
    private String imagePath;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        logger.info("Initiating the dao call for the save category data");
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category save = categoryRepository.save(category);
        logger.info("Completed the dao call for the save category data");
        return modelMapper.map(save, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        logger.info("Initiating the dao call for the update category data for id:{}", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found "));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());
        Category save = categoryRepository.save(category);
        logger.info("Completed the dao call for the update category data for id:{}", categoryId);
        return modelMapper.map(save, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        logger.info("Initiating the dao call for the delete category data for id:{}", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found "));
        String fullPath= imagePath+ category.getCoverImage();
        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        categoryRepository.delete(category);
        logger.info("Completed the dao call for the delete category data for id:{}", categoryId);
    }


    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating the dao call for the all get category data");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> all = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> response = Helper.getPageableResponse(all, CategoryDto.class);
        logger.info("Completed the dao call for the all get category data");
        return response;
    }

    @Override
    public CategoryDto getCategory(String categoryId) {
        logger.info("Initiating the dao call for the get category data for id:{}", categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        logger.info("Completed the dao call for the  get category data for id:{}", categoryId);
        return modelMapper.map(category, CategoryDto.class);
    }
}
