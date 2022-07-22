package ru.soft1.soft_shop_light.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soft1.soft_shop_light.configuration.CustomProperties;
import ru.soft1.soft_shop_light.model.ProductOrder;
import ru.soft1.soft_shop_light.util.converters.EmailMessageFabricator;

@Service
public class OrderService {

    @Autowired
    private MailService mailService;

    @Autowired
    private CustomProperties customProperties;

    public void sendByEmailWithoutDb(ProductOrder productOrder) {
        String fullOrder = EmailMessageFabricator.getFullOrder(productOrder);
        mailService.sendMessage(customProperties.getRecipientEmail(), "Order", fullOrder);
    }
}
