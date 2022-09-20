package ru.soft1.soft_shop_light.web.controllers.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.soft1.soft_shop_light.configuration.CustomProperties;
import ru.soft1.soft_shop_light.configuration.ValidationCustomer;
import ru.soft1.soft_shop_light.model.ProductOrder;
import ru.soft1.soft_shop_light.service.OrderService;
import ru.soft1.soft_shop_light.to.OrderPositionList;
import ru.soft1.soft_shop_light.to.ProductOrderForm;
import ru.soft1.soft_shop_light.util.ProductOrderUtil;
import ru.soft1.soft_shop_light.util.converters.EmailMessageFabricator;
import ru.soft1.soft_shop_light.util.validation.ValidationUtil;

@Slf4j
@RestController
@SessionAttributes({"userOrderPositions", "userOrderForm"})
@RequestMapping(value = UiProductOrderController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UiProductOrderController {

    static final String URL = "/ui/orders";

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomProperties customProperties;

    @GetMapping
    public String[] getOrderMessage
            (@ModelAttribute("userOrderPositions") OrderPositionList userOrderPositions,
             @ModelAttribute("userOrderForm") ProductOrderForm userProductOrderForm) {
        log.info("Get message string");
        ProductOrder order =  ProductOrderUtil.toProductOrder(userOrderPositions, userProductOrderForm);
        String[] str = {EmailMessageFabricator.getFullOrder(order)};
        log.debug(str[0]);
        return str;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendOrder(@ModelAttribute("userOrderPositions") OrderPositionList userOrderPositions,
                          @Validated(ValidationCustomer.Web.class) @ModelAttribute("userOrderForm")
                                         ProductOrderForm userProductOrderForm) {
        ValidationUtil.checkProductListIsEmpty(userOrderPositions);
        log.info("send order by email");
        if (customProperties.isSendToEmail()) {
            orderService.sendByEmailWithoutDb(ProductOrderUtil.toProductOrder(userOrderPositions, userProductOrderForm));
        }
        userOrderPositions.removeAll();
    }



}
