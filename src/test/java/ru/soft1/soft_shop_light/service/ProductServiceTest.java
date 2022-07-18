package ru.soft1.soft_shop_light.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.support.ProductTestData;
import ru.soft1.soft_shop_light.support.TimingExtension;
import ru.soft1.soft_shop_light.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void getAll() {
        List<Product> expected = ProductTestData.getAllProduct();
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.getAll(), expected);
    }

    @Test
    void get() {
        Product expected = ProductTestData.getProductOne();
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.get(1), expected);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> {productService.get(17);});
    }

    @Test
    void delete() {
        //todo
    }

    @Test
    void update() {
        //todo
    }

    @Test
    void create() {
        //todo
    }
}