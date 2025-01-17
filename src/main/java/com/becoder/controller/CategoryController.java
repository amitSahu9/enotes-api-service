package com.becoder.controller;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category){
        Boolean b = categoryService.saveCategory(category);
        if(b){
            return new ResponseEntity<>("saved successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        if(CollectionUtils.isEmpty(allCategories)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @GetMapping("/active-category")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponse> allCategories = categoryService.getActiveCategories();
        if(CollectionUtils.isEmpty(allCategories)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id){
        CategoryDto categoryById = categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(categoryById)){
            return new ResponseEntity<>("Category not found with id - " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryById, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
        Boolean deleted = categoryService.deleteCategoryById(id);
        if(deleted){
            return new ResponseEntity<>("Category with id - " + id + " was deleted successfully! ", HttpStatus.OK);
        }
        return new ResponseEntity<>("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
