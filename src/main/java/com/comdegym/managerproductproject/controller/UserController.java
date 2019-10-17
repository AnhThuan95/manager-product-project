package com.comdegym.managerproductproject.controller;

import com.comdegym.managerproductproject.model.Cart;
import com.comdegym.managerproductproject.model.Manufacturer;
import com.comdegym.managerproductproject.model.Product;
import com.comdegym.managerproductproject.repository.CartRepository;
import com.comdegym.managerproductproject.service.ManufacturerService;
import com.comdegym.managerproductproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private CartRepository cartRepository;

    @ModelAttribute("manufacturers")
    public Iterable<Manufacturer> allManufacturers() {
        return manufacturerService.findAll();
    }

    @ModelAttribute("carts")
    public Iterable<Cart> allCart() {
        return cartRepository.findAll();
    }

    @GetMapping("/")
    public ModelAndView showProduct(@RequestParam(defaultValue = "") String s, @PageableDefault(size = 5, sort = "name") Pageable pageInfo) {
        ModelAndView modelAndView = new ModelAndView("user/list");
        Page<Product> products = s.isEmpty() ? getPage(pageInfo) : search(s, pageInfo);
        modelAndView.addObject("keyword", s);
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    private Page<Product> getPage(Pageable pageInfo) {
        return productService.findAll(pageInfo);
    }

    private Page<Product> search(String s, Pageable pageInfo) {
        return productService.search(s, pageInfo);
    }

    @PostMapping("/user/add/{id}")
    public ModelAndView addProductToCart(@PathVariable Long id) {
        Cart cart = new Cart();
        Optional<Product> product = productService.findById(id);
        product.ifPresent(cart::setProduct);
        cartRepository.save(cart);
        ModelAndView modelAndView = new ModelAndView("/user/notification");
        return modelAndView;
    }

    @GetMapping("/user/cart")
    public ModelAndView showCart() {
        ModelAndView modelAndView = new ModelAndView("user/cart");
        return modelAndView;
    }

    @GetMapping("/user/delete/{id}")
    public ModelAndView deleteProductInCart(@PathVariable Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        ModelAndView modelAndView = new ModelAndView("user/delete");
        modelAndView.addObject("cart", cart.get());
        return modelAndView;
    }

    @PostMapping("/user/delete")
    public ModelAndView deletedProductInCart(@ModelAttribute("cart") Cart cart) {
        cartRepository.deleteById(cart.getId());

        ModelAndView modelAndView = new ModelAndView("redirect:/user/cart");
        return modelAndView;
    }
}
