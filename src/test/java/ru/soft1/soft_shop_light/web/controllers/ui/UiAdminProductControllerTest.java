package ru.soft1.soft_shop_light.web.controllers.ui;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.soft1.soft_shop_light.configuration.CustomProperties;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.service.ProductService;
import ru.soft1.soft_shop_light.support.ProductTestData;
import ru.soft1.soft_shop_light.util.exception.NotFoundException;
import ru.soft1.soft_shop_light.web.AuthorizationUtil;
import ru.soft1.soft_shop_light.web.controllers.AbstractControllerTest;
import ru.soft1.soft_shop_light.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.soft1.soft_shop_light.util.exception.ErrorType.VALIDATION_ERROR;

@Slf4j
class UiAdminProductControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UiAdminProductController.URL + '/';

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomProperties properties;

    @Test
    void getAll() throws Exception {
        List<Product> expected = ProductTestData.getAllProduct();
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProductTestData.PRODUCT_MATCHER.contentJson(expected));
    }

    @Test
    void setAvailable() throws Exception{
        perform(MockMvcRequestBuilders.post(REST_URL +"available/"+ 5)
                .param("available", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertTrue(productService.get(5).isAvailable());
    }

    @Test
    void setNds() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL +"nds/"+ 5)
                .param("isNds", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertTrue(productService.get(5).isNdsInclude());
    }

    @Test
    void setTechSupport() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL +"techSupport/"+ 5)
                .param("isTechSupport", "false")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertFalse(productService.get(5).isRequiredTechnicalSupport());
    }


    @Test
    void get() throws Exception {
        Product expected = ProductTestData.getProductOne();
        perform(MockMvcRequestBuilders.get(REST_URL + expected.getId())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProductTestData.PRODUCT_MATCHER.contentJson(expected));
    }

    @Test
    void invalidGet() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + 100)
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception{
        perform(MockMvcRequestBuilders.delete(REST_URL + 4)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertThrows(NotFoundException.class, () -> productService.get(4));
    }

    //попытка удалить связанную сущность
    @Test
    void deleteInvalid() throws Exception{
        perform(MockMvcRequestBuilders.delete(REST_URL + 3)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andExpect(status().isConflict())
                .andDo(print());
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.get(3), ProductTestData.getProductThree());
    }

    @Test
    void deleteNotFound() throws Exception{
        perform(MockMvcRequestBuilders.delete(REST_URL + 100)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        Product expected = ProductTestData.getProductOne();
        expected.setName("New name");
        perform(MockMvcRequestBuilders.put(REST_URL + expected.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties))
                .content( JsonUtil.writeValue(expected)))
                .andExpect(status().isNoContent());
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.get(expected.id()), expected);
    }

    @Test
    void updateInvalid() throws Exception{
        Product invalid = ProductTestData.getProductOne();
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL + invalid.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateHtmlUnsafe() throws Exception{
        Product updated = ProductTestData.getProductOne();
        updated.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL + updated.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void create() throws Exception {
        Product expected = ProductTestData.getNewProduct();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties))
                .content(JsonUtil.writeValue(expected)));

        expected.setId((long)Product.START_SEQ);
        Product actual = ProductTestData.PRODUCT_MATCHER.readFromJson(action);
        ProductTestData.PRODUCT_MATCHER.assertMatch(actual, expected);
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.get(expected.getId()), expected);
    }

    @Test
    void createInvalid() throws Exception {
        Product invalid = ProductTestData.getNewProduct();
        invalid.setName("");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void saveOrUpdate() throws Exception {
        //todo
    }

    //todo алфавитный указатель
    //todo поисковое окно
    //todo статьи на главной
    //todo список самвых покупаемых товаров на главной
    //todo создать ProductTo передавать без параметра эвейлебл и без параметра - mostPopular (если его сделать,
    // подумать как сделать список для витрины маст популяр)
}