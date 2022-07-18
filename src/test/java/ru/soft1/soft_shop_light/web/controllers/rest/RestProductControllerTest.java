package ru.soft1.soft_shop_light.web.controllers.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.service.ProductService;
import ru.soft1.soft_shop_light.support.ProductTestData;
import ru.soft1.soft_shop_light.web.controllers.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class RestProductControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestProductController.REST_URL + '/';

    @Autowired
    private ProductService productService;

    @Test
    void getAll() throws Exception {
        List<Product> expected = ProductTestData.getAllProduct();
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProductTestData.PRODUCT_MATCHER.contentJson(expected));
    }

    @Test
    void get() {
        //todo
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