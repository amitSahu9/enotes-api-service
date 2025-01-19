package com.becoder.service.impl;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.exception.ResourceAlreadyExistException;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.CategoryRepository;
import com.becoder.service.CategoryService;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) throws Exception{
//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        category.setDescription(categoryDto.getDescription());
//        category.setIsActive(categoryDto.getIsActive());
        Category category = modelMapper.map(categoryDto, Category.class);

        if(ObjectUtils.isEmpty(categoryDto.getId())){
            category.setIsDeleted(false);
            category.setCreatedBy(1);
            category.setCreatedOn(new Date());
        }
        else{
            updateCategory(category);
        }

        Category saveCategory = categoryRepository.save(category);
        if(ObjectUtils.isEmpty(saveCategory)){
            return false;
        }
        return true;
    }

    private void updateCategory(Category category) throws Exception{
        Optional<Category> byId = categoryRepository.findById(category.getId());
        if(byId.isPresent()){
            Category existingCategory = byId.get();
            if(category.getId() == existingCategory.getId()){
                throw new ResourceAlreadyExistException("Category with id " + category.getId() + " already exists");
            }
            category.setCreatedBy(existingCategory.getCreatedBy());
            category.setCreatedOn(existingCategory.getCreatedOn());
            category.setIsDeleted(existingCategory.getIsDeleted());
            category.setUpdatedBy(1);
            category.setUpdatedOn(new Date());
        }
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> allCategories = categoryRepository.findByIsDeletedFalse();
        List<CategoryDto> collected = allCategories.stream().map(cat -> modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return collected;
    }

    @Override
    public List<CategoryResponse> getActiveCategories() {
        List<Category> activeCategories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse> collectedActives= activeCategories.stream().map(cat -> modelMapper.map(cat, CategoryResponse.class)).collect(Collectors.toList());
        return collectedActives;
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws Exception {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id = " + id));
        if(!ObjectUtils.isEmpty(category)){
//            if(category.getName() == null){
//                throw new IllegalArgumentException("name is null");
//            }
//            category.getName().toUpperCase();
            return modelMapper.map(category, CategoryDto.class);
        }
        return null;
    }

    public Boolean deleteCategoryById(Integer id){
        Optional<Category> byId = categoryRepository.findById(id);
        if(byId.isPresent()){
            Category category = byId.get();
            category.setIsDeleted(true);
            categoryRepository.save(category);
            return true;
        }
        return false;
    }
}
