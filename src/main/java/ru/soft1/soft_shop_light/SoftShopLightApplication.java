package ru.soft1.soft_shop_light;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoftShopLightApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftShopLightApplication.class, args);
    }

    //todo тогда необходимо дополнителньое поле для продукт available - включающее и отключающее видимость продукта
    //todo сделать метод формирующий текстовое сообщение заказа из позиций.
    //todo сохранять в бд id заказа как текст?
    //todo метод getAll - админский, извлекает все записи включая недоступные
    //todo сделать метод getAvailable с сортировкой
    //todo обсудить с Андреем ситуацию с позициями и заказами, а так же соответствии цены

    //Вариант:
    //позиции заказа сохраняются как отдельные сущности (как и прежде),
    // но при этом записывают информацию по продукту как сводку полей продукта (копию на текущий момент?)

}
