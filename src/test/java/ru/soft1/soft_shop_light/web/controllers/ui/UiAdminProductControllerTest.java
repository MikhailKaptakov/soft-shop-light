package ru.soft1.soft_shop_light.web.controllers.ui;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
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

@Slf4j
@Transactional
class UiAdminProductControllerTest extends AbstractControllerTest {

    private static final String URL = UiAdminProductController.URL + '/';

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomProperties properties;

    @Test
    void getAll() throws Exception {
        List<Product> expected = ProductTestData.getAllProduct();
        perform(MockMvcRequestBuilders.get(URL)
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProductTestData.PRODUCT_MATCHER.contentJson(expected));
    }

    @Test
    void setAvailable() throws Exception{
        perform(MockMvcRequestBuilders.post(URL +"available/"+ 5)
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
        perform(MockMvcRequestBuilders.post(URL +"nds/"+ 5)
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
        perform(MockMvcRequestBuilders.post(URL +"techSupport/"+ 5)
                .param("isTechSupport", "false")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertFalse(productService.get(5).isRequiredTechnicalSupport());
    }

    @Test
    void setFavorite() throws Exception {
        perform(MockMvcRequestBuilders.post(URL +"favorite/"+ 5)
                .param("favorite", "false")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertFalse(productService.get(5).isFavorite());
    }

    @Test
    void get() throws Exception {
        Product expected = ProductTestData.getProductOne();
        perform(MockMvcRequestBuilders.get(URL + expected.getId())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProductTestData.PRODUCT_MATCHER.contentJson(expected));
    }

    @Test
    void invalidGet() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + 100)
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception{
        perform(MockMvcRequestBuilders.delete(URL + 4)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertThrows(NotFoundException.class, () -> productService.get(4));
    }

    //попытка удалить связанную сущность
    @Test
    void deleteInvalid() throws Exception{
        perform(MockMvcRequestBuilders.delete(URL + 3)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andExpect(status().isConflict())
                .andDo(print());
        ProductTestData.PRODUCT_MATCHER.assertMatch(productService.get(3), ProductTestData.getProductThree());
    }

    @Test
    void deleteNotFound() throws Exception{
        perform(MockMvcRequestBuilders.delete(URL + 100)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }


    //todo сделать полноценный рендер модального окна верификации (а не текстовое представление заказа)

    /* TODO Статьи на главной странице
        * !!!!
        !!!! в index - список превьюшек к статьям с заголовками и label
        * !!!!!
     */

    /*todo методы оформления статей
        todo добавить методы для вставки изображения в качестве бекграунд имейдж (за счёт css-класса))
        todo добавить методы для вставки лого в качестве бекграунд имейдж (за счёт css-класса))
        todo добавить методы рендера, которые передают css-классы в div элемент контейнера статьи
    */

    /*todo сделать сущность изображение, которая хранит изображение как байты и формат изображения (через отдельный список типов енам)
    *  - сделать методы дотсупа к изображениям с юай
    *  - добавить модуль, который проводит обработку статей и
    *  по классу плюс спецаттрибут хранящий айди изображения вставляет изображение в статью
    * */

    /*todo сохранение заказов в базе данных - в переработанном формате
    *  - убрана связность товаров и заказов, в заказе сохраняем список со значениями позиций заказа на момент оформления,
    * и список с данными формы регистрации в формате джейсон */

    //todo придумать защиту от спам атак заказами на почту
   /*todo добавить проверку на заказ, если заказ в артрибутах сессии пуст
      или не отвечает требованиям верификации таким
      как отсутствие части необходимой информации о заказчике выдать исключение*/

    //todo настроить подсветку активной страницы (в инит js страницы)
    //todo добавить в панель навигации ссылки на:
    // -home page
    // -about
    //todo Страница о нас

}