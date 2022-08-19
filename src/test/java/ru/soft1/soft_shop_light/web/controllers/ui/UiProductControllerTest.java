package ru.soft1.soft_shop_light.web.controllers.ui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.service.ProductService;
import ru.soft1.soft_shop_light.support.ProductTestData;
import ru.soft1.soft_shop_light.web.controllers.AbstractControllerTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class UiProductControllerTest extends AbstractControllerTest {

    private static final String URL = UiProductController.URL + '/';

    @Autowired
    private ProductService productService;

    @Test
    void getAllAvailable() throws Exception {
        List<Product> expected = ProductTestData.getAllAvailableProduct();
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProductTestData.PRODUCT_MATCHER.contentJson(expected));
    }

    @Test
    void getAvailable() throws Exception {
        Product expected = ProductTestData.getProductOne();
        perform(MockMvcRequestBuilders.get(URL + expected.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProductTestData.PRODUCT_MATCHER.contentJson(expected));
    }

    @Test
    void invalidGetAvailable() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + 100))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void GetNotAvailable() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + 5))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getAllFavoriteAvailable() {
        //todo
    }
}