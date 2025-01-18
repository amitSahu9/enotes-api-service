package com.becoder.service.impl;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
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
    public Boolean saveCategory(CategoryDto categoryDto) {
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

    private void updateCategory(Category category) {
        Optional<Category> byId = categoryRepository.findById(category.getId());
        if(byId.isPresent()){
            Category existingCategory = byId.get();
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
    public CategoryDto getCategoryById(Integer id) {
        Optional<Category> byId = categoryRepository.findByIdAndIsDeletedFalse(id);
        if(byId.isPresent()){
            return modelMapper.map(byId.get(), CategoryDto.class);
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
