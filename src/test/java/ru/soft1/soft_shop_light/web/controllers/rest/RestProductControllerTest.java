package ru.soft1.soft_shop_light.web.controllers.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.service.ProductService;
import ru.soft1.soft_shop_light.support.ProductTestData;
import ru.soft1.soft_shop_light.web.controllers.AbstractControllerTest;
import ru.soft1.soft_shop_light.web.json.JacksonObjectMapper;
import ru.soft1.soft_shop_light.web.json.JsonUtil;

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
    void get() throws Exception {
        Product expected = ProductTestData.getProductOne();
        perform(MockMvcRequestBuilders.get(REST_URL + expected.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProductTestData.PRODUCT_MATCHER.contentJson(expected));
    }

    @Test
    void invalidGet() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + 100))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
        //todo exception handling
    }

    @Test
    void delete() throws Exception{
/*        perform(MockMvcRequestBuilders.post(REST_URL + 100))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());*/
        //todo
    }

    @Test
    void deleteNotFound() throws Exception{
        //todo
    }

    @Test
    void update() throws Exception {
        Product expected = ProductTestData.getProductOne();
        expected.setName("New name");
        perform(MockMvcRequestBuilders.put(REST_URL + expected.getId())
                .contentType(MediaType.APPLICATION_JSON)
                /*.with(userHttpBasic(admin))*/ //todo security
                .content( JsonUtil.writeValue(expected)))
                .andExpect(status().isNoContent());
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.get(expected.id()), expected);
    }

    @Test
    void updateDuplicate() throws Exception{
        //todo
    }

    @Test
    void updateInvalid() throws Exception{
        //todo
    }

    @Test
    void updateHtmlUnsafe() throws Exception{
        //todo
    }

    @Test
    void create() throws Exception {
        Product expected = ProductTestData.getNewProduct();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                /*.with(userHttpBasic(user))*///todo
                .content(JsonUtil.writeValue(expected)));

        expected.setId((long)Product.START_SEQ);
        Product actual = ProductTestData.PRODUCT_MATCHER.readFromJson(action);
        ProductTestData.PRODUCT_MATCHER.assertMatch(actual, expected);
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.get(expected.getId()), expected);
    }

    @Test
    void createDuplicate() throws Exception{
        //todo
    }

    @Test
    void createInvalid() throws Exception {
        //todo
    }

    @Test
    void createHtmlUnsafe() throws Exception{
        //todo
    }
}