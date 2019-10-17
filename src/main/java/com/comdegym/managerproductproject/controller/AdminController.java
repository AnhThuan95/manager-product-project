package com.comdegym.managerproductproject.controller;

import com.comdegym.managerproductproject.model.Manufacturer;
import com.comdegym.managerproductproject.model.Product;
import com.comdegym.managerproductproject.model.UploadImage;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
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
    public ModelAndView showListProduct(@RequestParam(defaultValue = "") String s, @PageableDefault(size = 5, sort = "name") Pageable pageInfo) {
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
        modelAndView.addObject("image", new UploadImage());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createdProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, @RequestParam String urlImage) {
        new ProductValidator().validate(product, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("products/create");
            modelAndView.addObject("image", new UploadImage());
            return modelAndView;
        }

        product.setUrlImage(urlImage);
        productService.save(product);

        ModelAndView modelAndView = new ModelAndView("products/create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("image", new UploadImage());
        modelAndView.addObject("message", "New product created successfully");
        return modelAndView;

    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("products/edit");
        modelAndView.addObject("product", product.get());
        modelAndView.addObject("image", new UploadImage());

        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editedProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, @RequestParam String urlImage) {
        new ProductValidator().validate(product, bindingResult);
        ModelAndView modelAndView = new ModelAndView("products/edit");
        if (!bindingResult.hasFieldErrors()) {
            product.setUrlImage(urlImage);
            productService.save(product);

            modelAndView.addObject("message", "Product updated successfully");
        }
        modelAndView.addObject("product", product);
        modelAndView.addObject("urlImage", product.getUrlImage());
        modelAndView.addObject("image", new UploadImage());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("products/delete");
        modelAndView.addObject("product", product.get());
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

    @PostMapping("/uploadFileCreate")
    public ModelAndView uploadFileCreate(UploadImage uploadImage) {
        ModelAndView modelAndView = new ModelAndView("products/create");
        try {
            MultipartFile multipartFile = uploadImage.getMultipartFile();
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(this.getFolderUpload(), fileName);
            multipartFile.transferTo(file);
            modelAndView.addObject("multipartFile", fileName);
            modelAndView.addObject("urlImage", fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("image", new UploadImage());
        return modelAndView;
    }

    @GetMapping("/{id}/uploadFileEdit")
    public ModelAndView uploadFileEdit(UploadImage uploadImage, @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("products/edit/{id}");
        try {
            MultipartFile multipartFile = uploadImage.getMultipartFile();
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(this.getFolderUpload(), fileName);
            multipartFile.transferTo(file);
            modelAndView.addObject("multipartFile", fileName);
            modelAndView.addObject("urlImage", fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Optional<Product> product = productService.findById(id);
        modelAndView.addObject("product", product);
        modelAndView.addObject("image", new UploadImage());
        return modelAndView;
    }

    private File getFolderUpload() {
        File folderUpload = new File(System.getProperty("user.home") + "/Uploads");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }
}
