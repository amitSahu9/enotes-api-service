package com.becoder.service;

import com.becoder.entity.Category;

import java.util.List;

public interface CategoryService {
    public Boolean saveCategory(Category category);

    public List<Category> getAllCategories();
}
