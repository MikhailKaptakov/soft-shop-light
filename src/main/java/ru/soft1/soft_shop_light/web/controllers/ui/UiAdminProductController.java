package ru.soft1.soft_shop_light.web.controllers.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
@RequestMapping(value = UiAdminProductController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UiAdminProductController {

    static final String URL = "/admin/ui/products";

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        log.info("get all");
        return productService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> create(@Validated(ValidationCustomer.Web.class) @RequestBody Product product) {
        log.info("create {}", product);
        checkNew(product);
        Product created = productService.create(product);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/" + created.getId()).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveOrUpdate(@Validated(ValidationCustomer.Web.class) Product product) {
        if (product.itsNew()) {
            log.info("create {}", product);
            checkNew(product);
            productService.create(product);
        } else {
            log.info("update {} with id={}", product, product.getId());
            ValidationUtil.assureIdConsistent(product, product.getId());
            productService.update(product);
        }
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable("id") long id) {
        log.info("get {}", id);
        return productService.get(id);
    }

    @PostMapping("/available/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setAvailable(@PathVariable long id, @RequestParam boolean available) {
        log.info(available ? "available {}" : "not available {}", id);
        productService.setAvailable(id, available);
    }

    @PostMapping("/nds/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setNds(@PathVariable long id, @RequestParam boolean isNds) {
        log.info(isNds ? "nds {}" : "not nds {}", id);
        productService.setNds(id, isNds);
    }

    @PostMapping("/techSupport/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setTechSupport(@PathVariable long id, @RequestParam boolean isTechSupport) {
        log.info(isTechSupport ? "nds {}" : "not nds {}", id);
        productService.setTechSupport(id, isTechSupport);
    }

    @PostMapping("/img/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setImage(@PathVariable long id, @RequestParam MultipartFile image) {
        productService.saveImage(id, image);
    }

    @PostMapping("/img/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable long id) {
        productService.deleteImage(id);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        log.info("delete {}", id);
        productService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(ValidationCustomer.Web.class) @RequestBody Product product, @PathVariable long id) {
        log.info("update {} with id={}", product, id);
        ValidationUtil.assureIdConsistent(product, id);
        productService.update(product);
    }
}

