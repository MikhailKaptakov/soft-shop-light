package ru.soft1.soft_shop_light;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoftShopLightApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftShopLightApplication.class, args);
    }

    //todo сделать метод формирующий текстовое сообщение заказа из позиций.
    //todo сохранять в бд id заказа как текст?

    //todo добавить секьюрити
    // роли - юзер, админ
    // зарегестрированный юзер имеет привелегии - возможность просмотра своих ранее сделанных заказов и автозаполнение
    // формы данными с предыдущего заказа
    // админ - привелегии добавления удаления изменения позиций products

    //todo
    // rest controller заказы (просмотр, просмотр своих заказов)
    // добавить поле статус заказа
    // просмотр активных заказов
    // просмотр обработанных заказов
    // просмотр выполненных заказов
    // изменить статус заказа
    //установить итоговую стоимость


    //todo
    // структура заказа - айди, время регистрации, список позиций, статус заказа, итоговая стоимость заказа
    // позиции заказа -


    //todo 1 pos.
    // создать отправку сообщений по почте! При принятии заказа
}
