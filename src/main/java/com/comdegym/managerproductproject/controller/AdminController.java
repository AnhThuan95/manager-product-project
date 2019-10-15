package com.comdegym.managerproductproject.controller;

import com.comdegym.managerproductproject.model.Product;
import com.comdegym.managerproductproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ProductService productService;

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
    public ModelAndView createdProduct(@ModelAttribute ("product") Product product) {
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
    public ModelAndView editedProduct(@ModelAttribute ("product") Product product) {
        productService.save(product);

        ModelAndView modelAndView = new ModelAndView("/products/edit");
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "Product updated successfully");
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
    public ModelAndView deletedProduct(@ModelAttribute ("product") Product product) {
        productService.remove(product.getId());

        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        return modelAndView;
    }
}
