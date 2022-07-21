package ru.soft1.soft_shop_light.web.controllers.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.soft1.soft_shop_light.configuration.ValidationCustomer;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.service.ProductService;
import ru.soft1.soft_shop_light.util.validation.ValidationUtil;

import java.net.URI;
import java.util.List;

import static ru.soft1.soft_shop_light.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = RestProductController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestProductController {

    static final String REST_URL = "/rest/products";

    @Autowired
    private ProductService productService;

    //todo getAllAvailable доступен всем пользователям

    // доступен исключительно админу
    @GetMapping
    public List<Product> getAll() {
        log.info("get all");
        return productService.getAll();
    }

    @GetMapping("/available")
    public List<Product> getAllAvailable() {
        log.info("get available");
        return productService.getAvailable();
    }

    // доступен исключительно админу
    @GetMapping("/{id}")
    public Product get(@PathVariable("id") long id) {
        log.info("get {}", id);
        return productService.get(id);
    }

    // доступен исключительно админу
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        productService.delete(id);
    }

    // доступен исключительно админу
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(ValidationCustomer.Web.class) @RequestBody Product product, @PathVariable long id) {
        log.info("update {} with id={}", product, id);
        ValidationUtil.assureIdConsistent(product, id);
        productService.update(product);
    }

    // доступен исключительно админу
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> create(@Validated(ValidationCustomer.Web.class) @RequestBody Product product) {
        log.info("create {}", product);
        checkNew(product);
        Product created = productService.create(product);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + created.getId()).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}