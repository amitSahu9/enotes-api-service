package com.becoder.controller;

import com.becoder.entity.Category;
import com.becoder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
        Boolean b = categoryService.saveCategory(category);
        if(b){
            return new ResponseEntity<>("saved successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
        List<Category> allCategories = categoryService.getAllCategories();
        if(CollectionUtils.isEmpty(allCategories)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }
}
