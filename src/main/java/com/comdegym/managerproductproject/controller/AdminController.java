package com.comdegym.managerproductproject.controller;

import com.comdegym.managerproductproject.model.Product;
import com.comdegym.managerproductproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        modelAndView.addObject("message", "created");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProduct() {
        ModelAndView modelAndView = new ModelAndView("/products/edit");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct() {
        ModelAndView modelAndView = new ModelAndView("/products/delete");
        return modelAndView;
    }
}
