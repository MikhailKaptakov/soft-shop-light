package ru.soft1.soft_shop_light.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.soft1.soft_shop_light.model.OrderPosition;
import ru.soft1.soft_shop_light.to.OrderPositionList;
import ru.soft1.soft_shop_light.to.ProductOrderForm;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@SessionAttributes({"userOrderPositions", "userOrderForm"})
@RequestMapping(value = ProductController.URL)
public class ProductController {
    static final String URL = "/products";

    @ModelAttribute(name = "userOrderPositions")
    public OrderPositionList orderPositions() {
        return new OrderPositionList();
    }

    @ModelAttribute(name = "userOrderForm")
    public ProductOrderForm orderForm() {
        return new ProductOrderForm();
    }

    @GetMapping
    public String openProductsPage() {
        return "user-products";
    }

}
