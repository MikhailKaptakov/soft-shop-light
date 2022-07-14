package ru.soft1.soft_shop_light.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.soft1.soft_shop_light.model.Product;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/products")
@SessionAttributes("currentOrder")
public class ProductController {

    @ModelAttribute("products")
    public void addHardcodeData(Model model) {
        long index = 1;
        List<Product> products = Arrays.asList(
                new Product(index, "prod1", "vendor1", "country1",
                        "33 minutes" , "description", 1000, 5,
                        true, true),
                new Product(index++, "prod2", "vendor2", "country2",
                        "33 years" , "undescription", 5, 550,
                        true, true),
                new Product(index, "prod3", "vendor3", "country3",
                        "5 seconds" , "useless program", 100000, 5,
                        false, false)
        );
        products.sort(Comparator.comparingInt(Product::getPrice));
        model.addAttribute("products",products);
    }

    @GetMapping
    public String showAllProducts() {
        log.debug("get /products");
        return "products";
    }

}
