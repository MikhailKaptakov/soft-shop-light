package ru.soft1.soft_shop_light.web.controllers.ui;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.soft1.soft_shop_light.configuration.CustomProperties;
import ru.soft1.soft_shop_light.model.Article;
import ru.soft1.soft_shop_light.service.ArticleService;
import ru.soft1.soft_shop_light.support.ArticleTestData;
import ru.soft1.soft_shop_light.util.exception.NotFoundException;
import ru.soft1.soft_shop_light.web.AuthorizationUtil;
import ru.soft1.soft_shop_light.web.controllers.AbstractControllerTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
class UiAdminArticleControllerTest extends AbstractControllerTest {

    private static final String URL = UiAdminArticleController.URL + '/';

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CustomProperties properties;

    @Test
    void getAll() throws Exception{
        List<Article> expected = ArticleTestData.getAll();
        perform(MockMvcRequestBuilders.get(URL)
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ArticleTestData.ARTICLE_MATCHER.contentJson(expected));
    }

    @Test
    void get() throws Exception {
        Article expected = ArticleTestData.getFirst();
        perform(MockMvcRequestBuilders.get(URL + expected.getId())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ArticleTestData.ARTICLE_MATCHER.contentJson(expected));
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
        perform(MockMvcRequestBuilders.delete(URL + ArticleTestData.FIRST_ID)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertThrows(NotFoundException.class, () -> articleService.get(ArticleTestData.FIRST_ID));
    }

    @Test
    void deleteNotFound() throws Exception{
        perform(MockMvcRequestBuilders.delete(URL + 100)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void setAvailable() throws Exception {
        perform(MockMvcRequestBuilders.post(URL +"available/"+ 3)
                .param("available", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .with(AuthorizationUtil.adminAuth(properties)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertTrue(articleService.get(3).isAvailable());
    }
}