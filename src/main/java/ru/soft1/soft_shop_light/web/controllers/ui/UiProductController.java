package ru.soft1.soft_shop_light.web.controllers.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.service.ProductService;
import ru.soft1.soft_shop_light.to.OrderPositionList;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = UiProductController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UiProductController {

    static final String URL = "/ui/products";

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