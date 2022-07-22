package ru.soft1.soft_shop_light.web.controllers.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.service.ProductService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = RestProductController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestProductController {

    static final String REST_URL = "/rest/products";

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllAvailable() {
        log.info("get All available");
        return productService.getAllAvailable();
    }

    @GetMapping("/{id}")
    public Product getAvailable(@PathVariable("id") long id) {
        log.info("get available");
        return productService.getAvailable(id);
    }

}