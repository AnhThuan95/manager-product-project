package com.comdegym.managerproductproject.validator;

import com.comdegym.managerproductproject.model.Product;
import com.comdegym.managerproductproject.service.ProductService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ProductValidator implements Validator {
    private ProductService productService;

    public ProductValidator(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        String name = product.getName();
        String description = product.getDescription();
        Long price = product.getPrice();
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "description", "description.empty");
        ValidationUtils.rejectIfEmpty(errors, "price", "price.empty");
        if (name.length()>128) {
            errors.rejectValue("name", "name.length");
        }
    }
}
