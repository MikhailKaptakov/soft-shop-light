package ru.soft1.soft_shop_light.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.soft1.soft_shop_light.model.ProductOrder;
import ru.soft1.soft_shop_light.service.ProductService;


@Slf4j
@Controller
@RequestMapping("/products")
@SessionAttributes(ProductController.currentOrderAttribute)
public class ProductController {

    public static final String currentOrderAttribute = "currentOrder";

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ModelAttribute(currentOrderAttribute)
    public ProductOrder createNewOrder() {
        return new ProductOrder();
    }

    @GetMapping
    public String getAll(Model model) {
        log.debug("get all products");
        model.addAttribute("products", productService.getAll()); //todo getAvailable
        return "products";
    }

    @PostMapping("/{id}")
    //todo переработать, передавать в теле количество
    public String addToOrder(Model model, @PathVariable("id") long id) {
        ProductOrder order = (ProductOrder) model.getAttribute(currentOrderAttribute);
        log.debug(order.toString());
        //todo check not null order
        order.addProduct(productService.get(id));
        log.debug("product: "+ id +"added to order");
        model.addAttribute(currentOrderAttribute, order);
        return "forward:/products";
    }

}
