package ru.soft1.soft_shop_light.web.controllers.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.soft1.soft_shop_light.configuration.ValidationCustomer;
import ru.soft1.soft_shop_light.model.ProductOrder;
import ru.soft1.soft_shop_light.service.OrderService;

import static ru.soft1.soft_shop_light.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = UiProductOrderController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UiProductOrderController {

    static final String URL = "/ui/orders";

    @Autowired
    private OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendOrderToEmail(@Validated(ValidationCustomer.Web.class) @RequestBody ProductOrder order) {
        log.info("send {}", order);
        checkNew(order);
        orderService.sendByEmailWithoutDb(order);
    }

}
