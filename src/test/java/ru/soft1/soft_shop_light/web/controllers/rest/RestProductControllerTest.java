package ru.soft1.soft_shop_light.web.controllers.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.service.ProductService;
import ru.soft1.soft_shop_light.support.ProductTestData;
import ru.soft1.soft_shop_light.util.exception.NotFoundException;
import ru.soft1.soft_shop_light.web.controllers.AbstractControllerTest;
import ru.soft1.soft_shop_light.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.soft1.soft_shop_light.util.exception.ErrorType.VALIDATION_ERROR;

@Transactional
class RestProductControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestProductController.REST_URL + '/';

    @Autowired
    private ProductService productService;

    @Test
    void getAllAvailable() throws Exception {
        List<Product> expected = ProductTestData.getAllAvailableProduct();
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProductTestData.PRODUCT_MATCHER.contentJson(expected));
    }

    @Test
    void getAvailable() throws Exception {
        Product expected = ProductTestData.getProductOne();
        perform(MockMvcRequestBuilders.get(REST_URL + expected.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProductTestData.PRODUCT_MATCHER.contentJson(expected));
    }

    @Test
    void invalidGetAvailable() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + 100))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void GetNotAvailable() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + 5))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

}