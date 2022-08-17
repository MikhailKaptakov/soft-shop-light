package ru.soft1.soft_shop_light.util;

import ru.soft1.soft_shop_light.model.ProductOrder;
import ru.soft1.soft_shop_light.to.OrderPositionList;
import ru.soft1.soft_shop_light.to.ProductOrderForm;

import java.time.LocalDateTime;

public class ProductOrderUtil {

    public static ProductOrder toProductOrder(OrderPositionList orderPositionList, ProductOrderForm productOrderForm) {
        ProductOrder order = new ProductOrder();
        order.setPositions(orderPositionList.getPositions());
        order.setEmail(productOrderForm.getEmail());
        order.setTelephoneNumber(productOrderForm.getTelephoneNumber());
        order.setFirstname(productOrderForm.getFirstname());
        order.setSurname(productOrderForm.getSurname());
        order.setSecondName(productOrderForm.getSecondName());
        order.setCompanyName(productOrderForm.getCompanyName());
        order.setAddress(productOrderForm.getAddress());
        order.setComment(productOrderForm.getComment());
        setCurrentDateTime(order);
        return order;
    }

    public static void setCurrentDateTime(ProductOrder productOrder) {
        productOrder.setOrderDateTime(LocalDateTime.now());
    }
}
