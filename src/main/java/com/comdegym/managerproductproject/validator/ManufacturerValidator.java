package com.comdegym.managerproductproject.validator;

import com.comdegym.managerproductproject.model.Manufacturer;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ManufacturerValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Manufacturer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Manufacturer manufacturer = (Manufacturer) target;
        String name = manufacturer.getName();
        String country = manufacturer.getCountry();
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "country", "country.empty");
    }
}
