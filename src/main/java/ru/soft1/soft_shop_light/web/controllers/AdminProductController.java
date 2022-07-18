package ru.soft1.soft_shop_light.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.service.ProductService;

import javax.persistence.ManyToOne;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/product")
@SessionAttributes(ProductController.currentOrderAttribute)
public class AdminProductController {
    private ProductService productService;

    @Cacheable("products")
    @GetMapping
    public String getAll(Model model) {
        log.debug("get all products");
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }
}