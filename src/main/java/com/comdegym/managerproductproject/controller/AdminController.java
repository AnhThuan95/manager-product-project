package com.comdegym.managerproductproject.controller;

import com.comdegym.managerproductproject.model.Product;
import com.comdegym.managerproductproject.service.ProductService;
import com.comdegym.managerproductproject.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("")
    public ModelAndView showListProduct() {
        Iterable<Product> products = productService.findAll();
        ModelAndView modelAndView = new ModelAndView("/products/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createProduct() {
        ModelAndView modelAndView = new ModelAndView("/products/create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createdProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {
        new ProductValidator().validate(product, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("/products/create");
        }

        productService.save(product);

        ModelAndView modelAndView = new ModelAndView("/products/create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("message", "New product created successfully");
        return modelAndView;

    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/products/edit");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editedProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {
        new ProductValidator().validate(product, bindingResult);
        ModelAndView modelAndView = new ModelAndView("/products/edit");
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
        ModelAndView modelAndView = new ModelAndView("/products/delete");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView deletedProduct(@ModelAttribute("product") Product product) {
        productService.remove(product.getId());

        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        return modelAndView;
    }
}
