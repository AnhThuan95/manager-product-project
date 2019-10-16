package com.comdegym.managerproductproject.formatter;

import com.comdegym.managerproductproject.model.Manufacturer;
import com.comdegym.managerproductproject.service.ManufacturerService;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

public class ManufacturerFormatter implements Formatter<Optional<Manufacturer>> {
    private ManufacturerService manufacturerService;

    public ManufacturerFormatter(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @Override
    public Optional<Manufacturer> parse(String text, Locale locale) throws ParseException {
        return manufacturerService.findById(Long.valueOf(text));
    }

    @Override
    public String print(Optional<Manufacturer> object, Locale locale) {
        return object.toString();
    }
}
