package com.becoder.service;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.exception.ResourceNotFoundException;

import java.util.List;

public interface CategoryService {
    public Boolean saveCategory(CategoryDto category) throws Exception;

    public List<CategoryDto> getAllCategories();

    public List<CategoryResponse> getActiveCategories();

    public CategoryDto getCategoryById(Integer id) throws Exception;

    public Boolean deleteCategoryById(Integer id);
}
