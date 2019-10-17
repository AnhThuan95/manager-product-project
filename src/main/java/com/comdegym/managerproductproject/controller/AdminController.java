package com.comdegym.managerproductproject.controller;

import com.comdegym.managerproductproject.model.Manufacturer;
import com.comdegym.managerproductproject.model.Product;
import com.comdegym.managerproductproject.service.ManufacturerService;
import com.comdegym.managerproductproject.service.ProductService;
import com.comdegym.managerproductproject.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ManufacturerService manufacturerService;

    @ModelAttribute("manufacturers")
    public Iterable<Manufacturer> allManufacturers() {
        return manufacturerService.findAll();
    }

    @GetMapping("")
    public ModelAndView showListProduct(@RequestParam(defaultValue = "") String s,@PageableDefault(size = 1, sort = "name") Pageable pageInfo) {
        ModelAndView modelAndView = new ModelAndView("products/list");

        Page<Product> products = s.isEmpty() ? getPage(pageInfo) : search(s, pageInfo);
        modelAndView.addObject("keyword", s);
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createProduct() {
        ModelAndView modelAndView = new ModelAndView("products/create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createdProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {
        new ProductValidator().validate(product, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("products/create");
        }

        productService.save(product);

        ModelAndView modelAndView = new ModelAndView("products/create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("message", "New product created successfully");
        return modelAndView;

    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("products/edit");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editedProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {
        new ProductValidator().validate(product, bindingResult);
        ModelAndView modelAndView = new ModelAndView("products/edit");
        if (!bindingResult.hasFieldErrors()) {
            productService.save(product);

            modelAndView.addObject("message", "Product updated successfully");
        }
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("products/delete");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView deletedProduct(@ModelAttribute("product") Product product) {
        productService.remove(product.getId());

        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        return modelAndView;
    }

    private Page<Product> getPage(Pageable pageInfo) {
        return productService.findAll(pageInfo);
    }

    private Page<Product> search(String s, Pageable pageInfo) {
        return productService.search(s, pageInfo);
    }
}
