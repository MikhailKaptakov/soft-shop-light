package ru.soft1.soft_shop_light.web.controllers.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.service.ProductService;
import ru.soft1.soft_shop_light.to.OrderPositionList;

@Slf4j
@RestController
@RequestMapping(value = UiCartController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UiCartController {
    static final String URL = "/cart";

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/add/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProductToCart(@PathVariable("id") long id, @ModelAttribute OrderPositionList userOrderPositions) {
        Product product = productService.getAvailable(id);
        if (product != null) {
            userOrderPositions.addProduct(product);
            log.debug("Add product to cart: " + product.toString());
        }
    }

    @PostMapping(value = "/set/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setValue(@PathVariable("id") long id, @RequestParam int value, @ModelAttribute OrderPositionList userOrderPositions) {
        userOrderPositions.setValue(value, id);
        log.debug("Set value " + value + "to productId " + id);
    }

    @PostMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePosition(@PathVariable("id") long id, @ModelAttribute OrderPositionList userOrderPositions) {
        userOrderPositions.removePosition(id);
        log.debug("Deleted product " + id);
    }

    //todo кнопки добавить в корзину и кнопки внутри самой корзины должны вызывать методы js для отправки пост запроса
    //todo визуалку взять с образца, начинка - переработать через этот контроллер
    //todo сбросить сессию после отправки пост запроса заказа (для пересылки имейла с заказом)
}
