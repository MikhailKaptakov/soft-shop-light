package ru.soft1.soft_shop_light.web.controllers.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.soft1.soft_shop_light.service.ProductService;

@Slf4j
@RestController
@RequestMapping(value = RestProductController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestProductOrderController {

    static final String REST_URL = "/rest/orders";

    @Autowired
    private ProductService productService;

    //todo

    //todo sendByEmail
}
