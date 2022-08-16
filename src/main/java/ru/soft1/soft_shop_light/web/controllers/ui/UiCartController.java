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
@SessionAttributes("userOrderPositions")
@RequestMapping(value = UiCartController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UiCartController {
    static final String URL = "/ui/cart";

    @Autowired
    private ProductService productService;

    @GetMapping()
    public OrderPositionList getCartPositions
            (@ModelAttribute("userOrderPositions") OrderPositionList userOrderPositions) {
        log.info("get cart");
        return userOrderPositions;
    }

    @PostMapping(value = "/add/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProductToCart(@PathVariable("id") long id,
                                 @ModelAttribute("userOrderPositions") OrderPositionList userOrderPositions) {
        Product product = productService.getAvailable(id);
        if (product != null) {
            userOrderPositions.addProduct(product);
            log.debug("Add product to cart: " + product.toString());
        }
    }

    @PostMapping(value = "/minus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void minusProductFromCart(@PathVariable("id") long id,
                                 @ModelAttribute("userOrderPositions") OrderPositionList userOrderPositions) {
        Product product = productService.getAvailable(id);
        if (product != null) {
            userOrderPositions.deleteOne(id);
            log.debug("Deleted one product from cart: " + product.toString());
        }
    }

    @PostMapping(value = "/set/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setValue(@PathVariable("id") long id, @RequestParam int value,
                         @ModelAttribute("userOrderPositions") OrderPositionList userOrderPositions) {
        userOrderPositions.setValue(value, id);
        log.debug("Set value " + value + "to productId " + id);
    }

    @PostMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePosition(@PathVariable("id") long id,
                               @ModelAttribute("userOrderPositions") OrderPositionList userOrderPositions) {
        userOrderPositions.removePosition(id);
        log.debug("Deleted product " + id);
    }

    @PostMapping(value = "/clear")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearOrderPositionList(
            @ModelAttribute("userOrderPositions") OrderPositionList userOrderPositions) {
        userOrderPositions.removeAll();
        log.debug("Cart is clear");
    }


    //todo сбросить сессию после отправки пост запроса заказа (для пересылки имейла с заказом)
    //todo сделать навигационную панель для телефонов
    //todo сделать окошечко для ввода количества
    //todo сделать форму для отправления заказа (все запрашиваемые данные ProductOrderForm) при нажатии ок - сохраняет
    //данные формы и выдает окно с товарами С надписью вы хотите заказать: и две кнопки ок и отмена
    //затем данные записываются в текст письма и отправляются на почту
    //корзина очищается
}
