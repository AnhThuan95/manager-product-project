package com.comdegym.managerproductproject.controller;

import com.comdegym.managerproductproject.model.Manufacturer;
import com.comdegym.managerproductproject.service.ManufacturerService;
import com.comdegym.managerproductproject.validator.ManufacturerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("admin/manufacturer")
public class ManufacturerController {
    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("")
    public ModelAndView showListManufacturer() {
        Iterable<Manufacturer> manufacturers = manufacturerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/manufacturer/list");
        modelAndView.addObject("manufacturers", manufacturers);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createManufacturer() {
        ModelAndView modelAndView = new ModelAndView("/manufacturer/create");
        modelAndView.addObject("manufacturer", new Manufacturer());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createdManufacturer(@Valid @ModelAttribute("manufacturer") Manufacturer manufacturer, BindingResult bindingResult) {
        new ManufacturerValidator().validate(manufacturer, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("/manufacturer/create");
        }

        manufacturerService.save(manufacturer);

        ModelAndView modelAndView = new ModelAndView("/manufacturer/create");
        modelAndView.addObject("manufacturer", new Manufacturer());
        modelAndView.addObject("message", "New manufacturer created successfully");
        return modelAndView;

    }

    @GetMapping("/edit/{id}")
    public ModelAndView editManufacturer(@PathVariable Long id) {
        Optional<Manufacturer> manufacturer = manufacturerService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/manufacturer/edit");
        modelAndView.addObject("manufacturer", manufacturer);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editedManufacturer(@Valid @ModelAttribute("manufacturer") Manufacturer manufacturer, BindingResult bindingResult) {
        new ManufacturerValidator().validate(manufacturer, bindingResult);
        ModelAndView modelAndView = new ModelAndView("/manufacturer/edit");
        if (!bindingResult.hasFieldErrors()) {
            manufacturerService.save(manufacturer);

            modelAndView.addObject("message", "Manufacturer updated successfully");
        }
        modelAndView.addObject("manufacturer", manufacturer);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteManufacturer(@PathVariable Long id) {
        Optional<Manufacturer> manufacturer = manufacturerService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/manufacturer/delete");
        modelAndView.addObject("manufacturer", manufacturer);
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView deletedManufacturer(@ModelAttribute("manufacturer") Manufacturer manufacturer) {
        manufacturerService.remove(manufacturer.getId());

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/manufacturer");
        return modelAndView;
    }
}
