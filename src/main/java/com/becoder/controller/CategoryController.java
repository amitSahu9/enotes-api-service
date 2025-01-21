package com.becoder.controller;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.service.CategoryService;
import com.becoder.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category) throws Exception{
        Boolean b = categoryService.saveCategory(category);
        if(b){
            return CommonUtil.createErrorResponseMessage("saved success", HttpStatus.CREATED);
//            return new ResponseEntity<>("saved successfully", HttpStatus.CREATED);
        }
        return CommonUtil.createBuildResponseMessage("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
//        String nm = null;
//        nm.toUpperCase();
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        if(CollectionUtils.isEmpty(allCategories)){
            return ResponseEntity.noContent().build();
        }
//        return new ResponseEntity<>(allCategories, HttpStatus.OK);
        return CommonUtil.createBuildResponse(allCategories, HttpStatus.OK);
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
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception{
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
