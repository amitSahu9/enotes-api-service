package com.becoder.util;

import com.becoder.dto.CategoryDto;
import com.becoder.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Component
public class Validation {
    public void categoryValidation(CategoryDto categoryDto) {
        Map<String, Object> errors = new LinkedHashMap<>();
        if(ObjectUtils.isEmpty(categoryDto)) {
            throw new IllegalArgumentException("Category object should not be null or empty");
        }
        else {
            //validation name field
            if (ObjectUtils.isEmpty(categoryDto.getName())) {
                errors.put("name", "name field is empty or null");
            } else {
                if (categoryDto.getName().length() < 10) {
                    errors.put("name", "name length minimum 10");
                }
                if (categoryDto.getName().length() > 100) {
                    errors.put("name", "name length maximum 100");
                }
            }

            //validation description
            if(ObjectUtils.isEmpty(categoryDto.getDescription())) {
                errors.put("description", "description field is empty or null");
            }
            else {
                if (categoryDto.getDescription().length() < 10) {
                    errors.put("description", "description length minimum 10");
                }
                if (categoryDto.getDescription().length() > 100) {
                    errors.put("description", "description length maximum 100");
                }
            }

            //validation isActive
            if(ObjectUtils.isEmpty(categoryDto.getIsActive())) {
                errors.put("isActive", "isActive field is empty or null");
            }
            else {
                if (!(categoryDto.getIsActive() == TRUE || categoryDto.getIsActive() == FALSE)) {
                    errors.put("isActive", "invalid value isActive field");
                }
            }
        }

        if(!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
